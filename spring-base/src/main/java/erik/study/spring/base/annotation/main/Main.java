package erik.study.spring.base.annotation.main;

import erik.study.spring.base.annotation.config.SpringBeanDependencyConfig;
import erik.study.spring.base.annotation.factory.ConnectionFactory;
import erik.study.spring.base.annotation.model.Connection;
import erik.study.spring.base.annotation.model.ConnectionPool;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-context.xml");

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringBeanDependencyConfig.class);
        Object objectByGetBeanName = applicationContext.getBean("connectionFactory2");
        Object objectByGetReferenceBeanName  = applicationContext.getBean("&connectionFactory2");

        checkType(objectByGetBeanName);
        checkType(objectByGetReferenceBeanName);

        ConnectionPool connectionPool = applicationContext.getBean(ConnectionPool.class);

        Connection connection = connectionPool.getConnection();

        System.out.println(connectionPool);

    }

    public static void checkType(Object object) {

        System.out.println("connectionFactory is Connection:" + Boolean.toString(object instanceof Connection));

        System.out.println("connectionFactory is ConnectionFactory:" + Boolean.toString(object instanceof ConnectionFactory));
    }

}
