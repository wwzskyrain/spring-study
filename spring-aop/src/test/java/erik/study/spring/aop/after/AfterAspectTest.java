package erik.study.spring.aop.after;

import erik.study.spring.aop.after.service.Hello;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Date 2019-09-14
 * @Created by erik
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:beans.xml"})
public class AfterAspectTest {

    @Autowired
    private Hello hello;

    @Test
    public void test_hello(){
        hello.addUser("myName","password");
    }
}
