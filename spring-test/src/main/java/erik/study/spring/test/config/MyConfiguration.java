package erik.study.spring.test.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author erik.wang
 * @date 2020-02-16 10:11
 * @description
 */
@Configuration
@Import(MyImportSelector.class)
public class MyConfiguration {

    @Bean
    public MyPersonThree getMyPersonThree() {
        return new MyPersonThree();
    }

}
