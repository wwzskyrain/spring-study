package aop1.concert.xmlconfig;

import aop1.concert.Performance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-context-aop-config.xml")
public class AudienceTest {

    @Autowired
    private Performance performance;

    @Test
    public void test_spring_aop_with_xml_config(){
        performance.perform();
    }

}
