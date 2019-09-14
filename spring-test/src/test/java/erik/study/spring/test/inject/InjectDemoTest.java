package erik.study.spring.test.inject;

import erik.study.spring.test.inject.component.InjectDemo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Date 2019-09-14
 * @Created by erik
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:my-beans.xml"})
public class InjectDemoTest {

    private final static Logger logger = LoggerFactory.getLogger(InjectDemoTest.class);

    @Autowired
    private InjectDemo injectDemo;

    @Autowired
    private SimpleDriverDataSource dataSource;

    @Test
    public void test_inject() {

        logger.info("Url: {}", dataSource.getUrl());
        logger.info("User name: {}", dataSource.getUsername());
        logger.info("Password: {}", dataSource.getPassword());


        logger.info(injectDemo.getDbUrl());
        logger.info(injectDemo.getDbUserName());
        logger.info(injectDemo.getDbPassword());

    }

}
