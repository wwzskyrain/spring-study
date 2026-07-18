package erik.spring.schedule.thread.conflict;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        new AnnotationConfigApplicationContext(ScheduleConfig.class);
        System.out.println("Spring容器已启动，观察两个@Scheduled(cron=\"0/10 * * * * ?\")任务的执行时间……");
        System.out.println("默认单线程调度 -> 两个任务会串行执行，互相阻塞！");
        System.out.println("EventSchedule 耗时5s，OrderSchedule 耗时2s");
        System.out.println("由于单线程，EventSchedule 执行完后才会执行 OrderSchedule");
        System.out.println("导致 OrderSchedule 的整10s周期被破坏，实际执行时刻被延后：0、15、17、27、29……\n");

        Thread.sleep(30000);
        System.exit(0);
    }
}
