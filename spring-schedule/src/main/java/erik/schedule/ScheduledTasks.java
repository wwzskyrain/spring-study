package erik.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("test-fixedRate:The start time is now {}", dateFormat.format(new Date()));
        try {
            TimeUnit.SECONDS.sleep(1);
            log.info("test-fixedRate:The end time is now:{}", dateFormat.format(new Date()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedDelay = 5000)
    public void testDelay() {
        log.info("test-fixedDelay:The start time is now {}", dateFormat.format(new Date()));
        try {
            TimeUnit.SECONDS.sleep(1);
            log.info("test-fixedDelay:The end time is now:{}", dateFormat.format(new Date()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}