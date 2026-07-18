package erik.spring.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 线程池阻塞提交演示 - 精简版
 * 
 * 场景描述：
 * - 主线程一直往线程池中提交任务
 * - 线程池只有4个线程（corePoolSize=4, maximumPoolSize=4）
 * - 使用SynchronousQueue作为任务队列（容量为0）
 * - 当4个线程都在执行任务时，第5个任务提交会阻塞，直到有线程空闲
 * 
 * 核心原理（仅通过线程池配置实现，无需Semaphore）：
 * 
 * 1. ThreadPoolExecutor.execute() 流程：
 *    ├── 如果工作线程数 < corePoolSize: 创建新线程执行
 *    ├── 否则，尝试 offer(command) 到队列:
 *    │   ├── offer成功 → 任务进入队列
 *    │   └── offer失败（队列满）:
 *    │       ├── 如果工作线程数 < maximumPoolSize: 创建新线程执行
 *    │       └── 否则: 调用拒绝策略
 * 
 * 2. SynchronousQueue特性：
 *    ├── offer(e): 非阻塞，只有当有线程正在等待take()时才返回true，否则立即返回false
 *    ├── put(e): 阻塞，会一直阻塞直到有线程来take()
 * 
 * 3. 当corePoolSize=4, maximumPoolSize=4 + SynchronousQueue时：
 *    ├── 前4个任务：直接创建线程执行
 *    ├── 第5个任务：offer()返回false，线程数已达上限，触发拒绝策略
 *    ├── 拒绝策略中调用queue.put(r)阻塞主线程
 *    ├── 工作线程完成后调用getTask() → queue.take()，主线程被唤醒，任务被执行
 */
public class ThreadPoolBlockingDemo {

    private static final Logger log = LoggerFactory.getLogger(ThreadPoolBlockingDemo.class);

    /**
     * 线程池核心线程数 = 最大线程数 = 4
     * 这样线程池不会动态扩展，始终保持4个线程
     */
    private static final int CORE_POOL_SIZE = 4;
    private static final int MAX_POOL_SIZE = 4;
    
    /**
     * 线程空闲存活时间，由于corePoolSize == maximumPoolSize，此参数无效
     */
    private static final long KEEP_ALIVE_TIME = 60L;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    /**
     * 自定义线程池
     */
    private final ThreadPoolExecutor executor;

    public ThreadPoolBlockingDemo() {
        // 创建线程池
        // 参数说明：
        // 1. corePoolSize: 核心线程数，线程池维护的最小线程数
        // 2. maximumPoolSize: 最大线程数，线程池允许的最大线程数
        // 3. keepAliveTime: 非核心线程空闲存活时间
        // 4. unit: 时间单位
        // 5. workQueue: 任务队列，SynchronousQueue容量为0
        //    - offer()非阻塞，只有当有线程在等待take()时才返回true
        //    - 因此当所有线程都忙时，offer()会立即返回false
        // 6. threadFactory: 线程工厂
        // 7. handler: 自定义阻塞拒绝策略
        //    - 当线程池和队列都满时，调用queue.put()阻塞主线程
        this.executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TIME_UNIT,
                new SynchronousQueue<>(),  // 关键1：容量为0的队列
                new NamedThreadFactory("Worker"),
                new BlockingRejectedExecutionHandler()  // 关键2：阻塞拒绝策略
        );

        // 核心线程在空闲时也不会被回收
        executor.allowCoreThreadTimeOut(false);
    }

    /**
     * 提交任务到线程池
     * 当线程池已满时，此方法会阻塞直到有线程空闲
     *
     * @param task 要执行的任务
     * @return Future对象，可用于获取任务执行结果
     */
    public Future<?> submit(Runnable task) {
        // 直接调用executor.submit()
        // 由于使用了SynchronousQueue和自定义拒绝策略，
        // 当所有线程都忙时，submit会阻塞直到有线程空闲
        return executor.submit(task);
    }

    /**
     * 关闭线程池
     */
    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 自定义线程工厂，给线程命名便于调试
     */
    private static class NamedThreadFactory implements ThreadFactory {
        private final String prefix;
        private final java.util.concurrent.atomic.AtomicInteger counter = new java.util.concurrent.atomic.AtomicInteger(0);

        public NamedThreadFactory(String prefix) {
            this.prefix = prefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, prefix + "-" + counter.incrementAndGet());
            thread.setDaemon(false);
            thread.setPriority(Thread.NORM_PRIORITY);
            return thread;
        }
    }

    /**
     * 自定义阻塞拒绝策略
     * 
     * 当线程池无法接受新任务时（所有线程都忙），
     * 使用SynchronousQueue的put()方法阻塞主线程，
     * 直到有工作线程完成任务后来take()这个任务。
     * 
     * 工作流程：
     * 1. 主线程提交第5个任务，executor.execute()被调用
     * 2. 由于corePoolSize=4且所有线程都在忙，尝试offer到队列
     * 3. SynchronousQueue.offer()返回false（没有线程在等待take）
     * 4. 线程数已达maximumPoolSize=4，无法创建新线程
     * 5. 触发拒绝策略rejectedExecution()
     * 6. 调用executor.getQueue().put(r)阻塞主线程
     * 7. 某个工作线程完成任务后，调用getTask() -> queue.take()
     * 8. take()与put()配对成功，主线程被唤醒
     * 9. 工作线程获取到任务并执行
     */
    private static class BlockingRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                // 使用SynchronousQueue的put方法阻塞等待
                // SynchronousQueue的put会一直阻塞直到有消费者(take)
                // 这里的消费者就是工作线程的getTask()方法
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                // 如果等待过程中被中断，设置线程中断标志并抛出异常
                Thread.currentThread().interrupt();
                throw new RejectedExecutionException("Task submission interrupted", e);
            }
        }
    }

    /**
     * 主方法，演示阻塞提交功能
     */
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolBlockingDemo demo = new ThreadPoolBlockingDemo();

        try {
            log.info("========== 开始演示线程池阻塞提交 ==========");
            log.info("线程池配置：corePoolSize={}, maximumPoolSize={}", CORE_POOL_SIZE, MAX_POOL_SIZE);
            log.info("任务队列：SynchronousQueue(容量为0)");
            log.info("拒绝策略：BlockingRejectedExecutionHandler(queue.put阻塞)");
            log.info("每个任务执行时间：1秒");
            log.info("============================================\n");

            // 主线程连续提交20个任务
            for (int i = 1; i <= 20; i++) {
                final int taskId = i;
                long startTime = System.currentTimeMillis();

                log.info("主线程准备提交任务[{}]", taskId);

                // 提交任务，可能会阻塞
                // 当所有4个线程都忙时，submit会阻塞直到有线程空闲
                demo.submit(() -> {
                    log.info("任务[{}]开始执行，线程：{}", taskId, Thread.currentThread().getName());
                    try {
                        // 模拟任务执行时间：1秒
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    log.info("任务[{}]执行完毕，线程：{}", taskId, Thread.currentThread().getName());
                });

                long endTime = System.currentTimeMillis();
                long blockTime = endTime - startTime;

                // 如果阻塞时间超过100ms，说明发生了阻塞等待
                if (blockTime > 100) {
                    log.info("任务[{}]提交完成（阻塞等待了{}ms）", taskId, blockTime);
                } else {
                    log.info("任务[{}]提交完成（立即提交）", taskId);
                }
            }

            log.info("\n========== 所有任务提交完成，等待任务执行完毕 ==========");

            // 等待所有任务执行完毕
            TimeUnit.SECONDS.sleep(3);

        } finally {
            demo.shutdown();
            log.info("\n========== 线程池已关闭 ==========");
        }
    }
}