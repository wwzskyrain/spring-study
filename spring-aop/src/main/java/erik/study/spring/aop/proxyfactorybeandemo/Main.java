package erik.study.spring.aop.proxyfactorybeandemo;

import erik.study.spring.aop.proxyfactorybeandemo.service.Wait;
import erik.study.spring.aop.proxyfactorybeandemo.service.impl.Waiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Date 2019-09-15
 * @Created by erik
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("proxy-factory-bean-demo-beans.xml");
        Wait wait = applicationContext.getBean("proxyWaiter", Wait.class);

        wait.say(); //这是一个代理对象，对代理对象的方法调用则会触发增强逻辑呢。
        if (wait instanceof Waiter) { //wait根本不是Waiter，它是Wait接口的一个子类，辈分上和Waiter是兄弟呢。
            Waiter waiter = ((Waiter) wait);
            waiter.shangCai();
        }
        logger.info("-----------------------");
        Object sayHelloImpl = applicationContext.getBean("sayHelloImpl");
        if (sayHelloImpl instanceof Wait) {
            Wait waitImpl = ((Wait) sayHelloImpl);
            waitImpl.say(); //调用目标对象的方法是不会触发增强逻辑的，只有对代理对象的调用才能触发增强逻辑的。
        }
    }
}
