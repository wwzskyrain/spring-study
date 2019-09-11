package erik.study.spring.base.annotation.config;

import erik.study.spring.base.annotation.factory.ConnectionFactory;
import erik.study.spring.base.annotation.model.Connection;
import erik.study.spring.base.annotation.model.ConnectionPool;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringBeanDependencyConfig {

    @Bean(name = "connectionFactoryName2")
    @Qualifier("connectionFactoryQualifier2")
    public ConnectionFactory getConnectionFactory2() {
        return new ConnectionFactory("127.0.0.2", 2222);
    }

    @Bean(name = "connectionFactoryName3")
    @Qualifier("connectionFactoryQualifier3")
    public ConnectionFactory getConnectionFactory3() {
        return new ConnectionFactory("127.0.0.3", 333);
    }


    @Bean(name = "connectionPoolName2")
    public ConnectionPool connectionPool2(@Qualifier(value = "connectionFactoryQualifier2") Connection connectionFactory) {
        ConnectionPool connectionPool = new ConnectionPool();
        connectionPool.setConnection(connectionFactory);
        return connectionPool;
    }

    @Bean(name = "connectionPoolName3")
    public ConnectionPool connectionPool3(@Qualifier(value = "connectionFactoryName2") Connection connectionFactory) {
        ConnectionPool connectionPool = new ConnectionPool();
        connectionPool.setConnection(connectionFactory);
        return connectionPool;
    }

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(SpringBeanDependencyConfig.class);

        Object poolName2 = context.getBean("connectionPoolName2");

        Object poolName3 = context.getBean("connectionPoolName3");
        System.out.println(poolName2);
        System.out.println(poolName3);

        Connection pool2Connection = ((ConnectionPool) poolName2).getConnection();
        Connection pool3Connection = ((ConnectionPool) poolName3).getConnection();

//      当ConnectionFactory创建出来的Connection是单例模式的话，为true；
        System.out.println(pool2Connection == pool3Connection);

    }


}
