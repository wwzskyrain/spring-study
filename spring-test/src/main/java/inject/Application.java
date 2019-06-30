package inject; /**
 * @author erik.wang
 * @date 2019/05/19
 **/

import inject.component.InjectTest;
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

        InjectTest injectTest = ctx.getBean("injectTest", InjectTest.class);

        logger.info(injectTest.getDbUrl());
        logger.info(injectTest.getDbUserName());
        logger.info(injectTest.getDbPassword());


        ctx.close();

    }
}
