package erik.spring.schedule.thread.conflict;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderSchedule {

    private static final Logger log = LoggerFactory.getLogger(OrderSchedule.class);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

    @Scheduled(cron = "0/10 * * * * ?")
    public void doOrder() {
        String threadName = Thread.currentThread().getName();
        log.info("[OrderSchedule   | {}] start at {}", threadName, sdf.format(new Date()));
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("[OrderSchedule   | {}] end   at {}", threadName, sdf.format(new Date()));
    }
}
