package erik.spring.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author erik.wang
 * @date 2019/03/02
 **/
public class TaskSchedulerExample {

    @Autowired
    private TaskScheduler taskScheduler;

    public void setTaskScheduler(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    public void execute() {

        String cron = "0/5 * * * * ?";
        taskScheduler.schedule(new Printer(cron), new CronTrigger(cron));
        System.out.println("schedule over.");
    }

    public static class Printer implements Runnable {

        public Printer() {
        }

        String cron;

        public Printer(String cron) {
            this.cron = cron;
        }

        public void run() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
            System.out.println(String.format("current time:%s with cron:%s", dateFormat.format(new Date()), cron));
        }
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-context.xml");
        TaskSchedulerExample taskSchedulerExample = applicationContext.getBean(TaskSchedulerExample.class);

        taskSchedulerExample.execute();
    }

}
