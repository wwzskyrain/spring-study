package config;

import model.Connection;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringConfigBeanNameAndQualifier {

    @Bean(name = {"connectionName1", "connectionName2"})
    @Qualifier("connectionQualifier")
    public Connection getConnection() {
        Connection connection = new Connection();

        connection.setPort(1234);
        connection.setHost("111.111.111.111");

        return connection;
    }

    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfigBeanNameAndQualifier.class);

        Object connection1 = applicationContext.getBean("connectionName1");
        Object connection2 = applicationContext.getBean("connectionName2");

        System.out.println(connection1 == connection2);

    }

}
