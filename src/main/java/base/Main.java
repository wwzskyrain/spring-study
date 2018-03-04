package base;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {

        ApplicationContext context =new ClassPathXmlApplicationContext("spring-context-base.xml");

        Person person = context.getBean("person", Person.class);

        person.useAxe();
    }
}
