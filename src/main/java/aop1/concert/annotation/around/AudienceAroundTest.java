package aop1.concert.annotation.around;

import aop1.concert.Performance;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AudienceAroundConfig.class})
//带解决问题：这里指定了所要加载的配置文件是AudienceAroundConfig，可总是连带着加载另一个配置文件类AudienceConfig. maybe this just is a bug
public class AudienceAroundTest {

    @Autowired
    private Performance performance;

    @Test
    public void test_audience_around_aop(){
        performance.perform();
    }



}
