package aop1.concert.xmlconfig;

import aop1.concert.Performance;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context-aop-config.xml");

        Performance performance = context.getBean("performanceBean", Performance.class);

        performance.perform();

    }
}
