package erik.study.spring.test.inject; /**
 * @author erik.wang
 * @date 2019/05/19
 **/

import erik.study.spring.test.inject.component.InjectDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("my-beans.xml");

        SimpleDriverDataSource dataSource = (SimpleDriverDataSource) ctx.getBean("dataSource");

        logger.info("Url: {}", dataSource.getUrl());
        logger.info("User name: {}", dataSource.getUsername());
        logger.info("Password: {}", dataSource.getPassword());

        InjectDemo inject = ctx.getBean("injectTest", InjectDemo.class);


        logger.info(inject.getDbUrl());
        logger.info(inject.getDbUserName());
        logger.info(inject.getDbPassword());

        ctx.close();

    }
}
