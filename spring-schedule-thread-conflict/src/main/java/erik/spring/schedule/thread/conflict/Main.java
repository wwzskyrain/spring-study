package erik.spring.schedule.thread.conflict;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        // 场景一：默认单线程调度
        // new AnnotationConfigApplicationContext(DefaultScheduleConfig.class);
        // System.out.println("Spring容器已启动，观察两个@Scheduled(cron=\"0/10 * * * * ?\")任务的执行时间……");
        // System.out.println("默认单线程调度 -> 两个任务会串行执行，互相阻塞！");
        // System.out.println("EventSchedule 耗时5s，OrderSchedule 耗时2s");
        // System.out.println("由于单线程，EventSchedule 执行完后才会执行 OrderSchedule");
        // System.out.println("导致 OrderSchedule 的整10s周期被破坏，实际执行时刻被延后：0、15、17、27、29……\n");

        // 场景二：线程池调度
        new AnnotationConfigApplicationContext(ThreadPoolScheduleConfig.class);
        System.out.println("Spring容器已启动，观察两个@Scheduled(cron=\"0/10 * * * * ?\")任务的执行时间……");
        System.out.println("线程池调度(poolSize=5) -> 两个任务可并行执行，互不阻塞！");
        System.out.println("EventSchedule 耗时5s，OrderSchedule 耗时2s");
        System.out.println("由于线程池有空闲线程，两个任务在整10s时刻可同时触发");
        System.out.println("OrderSchedule 依然保持整10s周期：0、10、20、30……\n");

        Thread.sleep(30000);
        System.exit(0);
    }
}
