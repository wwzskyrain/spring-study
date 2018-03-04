package aop1.concert.annotation;


import aop1.concert.PerformanceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackageClasses = {PerformanceImpl.class})
public class AudienceConfig {

    @Bean
    public Audience audience(){
        System.out.println("public Audience audience()");
        return new Audience();
    }

}
