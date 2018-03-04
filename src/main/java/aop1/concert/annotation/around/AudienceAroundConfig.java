package aop1.concert.annotation.around;

import aop1.concert.Performance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackageClasses = {Performance.class})
public class AudienceAroundConfig {

    @Bean
    public AudienceAround getAudienceAroundAop() {
        System.out.println("public AudienceAround getAudienceAroundAop()");
        return new AudienceAround();
    }
}
