package erik.spring.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

class BeeperControl {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void beepForAnHour() {
        final Runnable beeper = new Runnable() {
            private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            public void run() {
                System.out.printf("%s beep\n",format.format(new Date()));
            }
        };
        final ScheduledFuture<?> beeperHandle =
                scheduler.scheduleAtFixedRate(beeper, 5, 5, SECONDS);

        scheduler.schedule(new Runnable() {
            private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            public void run() {
                beeperHandle.cancel(true);
                System.out.printf("beeperHandle is canceled at %s \n",format.format(new Date()));
            }
        }, 30, SECONDS);
    }

    public static void main(String[] args) {
        System.out.println(new Date());
        new BeeperControl().beepForAnHour();
        System.out.println("main over!");
    }
}