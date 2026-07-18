package erik.spring.schedule.thread.conflict;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EventSchedule {

    private static final Logger log = LoggerFactory.getLogger(EventSchedule.class);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

    @Scheduled(cron = "0/10 * * * * ?")
    public void doEvent() {
        String threadName = Thread.currentThread().getName();
        log.info("[EventSchedule   | {}] start at {}", threadName, sdf.format(new Date()));
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("[EventSchedule   | {}] end   at {}", threadName, sdf.format(new Date()));
    }
}
