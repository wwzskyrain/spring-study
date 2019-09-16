package erik.study.spring.aop.after.main;

import erik.study.spring.aop.after.service.Hello;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * @Date 2019-09-14
 * @Created by erik
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        TimeUnit.SECONDS.sleep(2);
        Hello hello = (Hello) context.getBean("helloImpl");
        hello.foo();
    }
}
