package main;

import listener.Teacher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {

        String contextPath = "spring-context-1.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(contextPath);

        System.out.println("application context should load over");

        Teacher teacher = context.getBean(Teacher.class);
        System.out.println(teacher.getName());

        teacher.publishQuestion("question:who is your lover?");



//        Resource resources = new ClassPathResource("spring-context-1.xml");
//
//        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
//
//        new XmlBeanDefinitionReader(beanFactory).loadBeanDefinitions(resources);


    }

}
