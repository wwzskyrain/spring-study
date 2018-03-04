package aop1.soundsystem.xmlconfig;

import aop1.soundsystem.CompactDisc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlConfigMain {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context-1.xml");

        CompactDisc compactDisc = context.getBean("compactDisc", CompactDisc.class);

        compactDisc.play();

    }

}
