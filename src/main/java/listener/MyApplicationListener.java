package listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class MyApplicationListener implements ApplicationListener {

    private int counter=0;

    public void onApplicationEvent(ApplicationEvent event) {
        System.out.printf("event %02d :%s\n",++counter,event);
    }
}
