package aop1.concert.annotation;

import aop1.concert.Performance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AudienceConfig.class})
public class AudienceTest {

    @Autowired
    private Performance performance;


    @Test
    public void test_performance_aop(){

        performance.perform();

    }

}
