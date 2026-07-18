package erik.spring.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * SynchronousQueue 原理验证
 * 
 * 验证三个关键点：
 * 1. size() 永远返回 0（不存储元素）
 * 2. offer() 在没有消费者时立即返回 false
 * 3. put() 会阻塞直到有消费者调用 take()
 */
public class SynchronousQueueDemo {

    private static final Logger log = LoggerFactory.getLogger(SynchronousQueueDemo.class);

    public static void main(String[] args) throws InterruptedException {
        // 创建一个 SynchronousQueue
        SynchronousQueue<String> queue = new SynchronousQueue<>();

        log.info("========== SynchronousQueue 原理验证 ==========\n");

        // ========== 验证1：size() 永远是 0 ==========
        log.info("验证1: size() 测试");
        log.info("  队列初始 size = {}", queue.size());

        // 尝试 offer（没有消费者，应该返回 false）
        boolean offerResult = queue.offer("test");
        log.info("  offer('test') 返回: {} (因为没有消费者)", offerResult);
        log.info("  offer 后 size = {} (仍然为0)\n", queue.size());

        // ========== 验证2：put() 会阻塞 ==========
        log.info("验证2: put() 阻塞测试");
        log.info("  主线程准备 put('hello')，当前没有消费者，将会阻塞...");

        // 启动一个消费者线程，1秒后调用 take()
        Thread consumer = new Thread(() -> {
            try {
                log.info("  [消费者] 等待2秒后调用 take()...");
                TimeUnit.SECONDS.sleep(2);
                log.info("  [消费者] 开始 take()...");
                String value = queue.take();
                log.info("  [消费者] take() 获取到: '{}'", value);
                log.info("  [消费者] take 后 size = {}", queue.size());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer-Thread");
        consumer.start();

        // 主线程调用 put()，会阻塞直到消费者调用 take()
        long startTime = System.currentTimeMillis();
        queue.put("hello");  // 这里会阻塞
        long blockTime = System.currentTimeMillis() - startTime;

        log.info("  主线程 put('hello') 返回，阻塞了 {}ms", blockTime);
        log.info("  put 后 size = {} (仍然为0!)\n", queue.size());

        consumer.join();

        // ========== 验证3：offer() 在有消费者等待时返回 true ==========
        log.info("验证3: 有消费者时 offer() 测试");

        // 启动一个消费者先调用 take() 等待
        Thread consumer2 = new Thread(() -> {
            try {
                log.info("  [消费者2] 先调用 take() 等待...");
                String value = queue.take();
                log.info("  [消费者2] take() 获取到: '{}'", value);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer2-Thread");
        consumer2.start();

        // 等待消费者2就绪
        TimeUnit.MILLISECONDS.sleep(500);

        // 现在 offer() 应该返回 true，因为有消费者在等待
        boolean offerResult2 = queue.offer("world");
        log.info("  有消费者时 offer('world') 返回: {} (因为消费者在等待)", offerResult2);
        log.info("  offer 后 size = {}\n", queue.size());

        consumer2.join();

        log.info("========== 验证完成 ==========");
        log.info("结论:");
        log.info("  1. SynchronousQueue 的 size() 永远是 0，因为它不存储元素");
        log.info("  2. offer() 是非阻塞的，没有消费者时立即返回 false");
        log.info("  3. put() 是阻塞的，会一直等待直到有消费者来 take()");
        log.info("  4. 元素直接在 put 线程和 take 线程间'传递'，从不进入队列存储");
    }
}