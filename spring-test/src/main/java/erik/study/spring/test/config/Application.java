package erik.study.spring.test.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author erik.wang
 * @date 2020-02-16 10:16
 * @description
 * erik_comment 这个例子就是要实践一下 @Import注解
 * 相关参考帖子: spring注解之@Import注解的三种使用方式(https://www.cnblogs.com/yichunguo/p/12122598.html)
 */
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfiguration.class);

        int i = 1;
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            logger.info("beanDefinitionName {} = {}", i++, beanDefinitionName);
        }

    }

}
