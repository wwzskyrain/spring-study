package erik.study.spring.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.context.annotation.Primary;
import erik.spring.util.aop.aspect.LogExecutionTimeAspect;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.TimeUnit;

/**
 * @author erik.wang
 * @Date 2020-01-09
 */

@ContextConfiguration
@EnableCaching
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "erik.study.spring.cache.service")
public class SpringConfig {

    @Primary
    @Bean
    public CacheManager getCacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        return caffeineCacheManager;
    }

    @Bean(value = "caffeineCacheManager")
    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        @NonNull Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                .maximumSize(10000).expireAfterWrite(5, TimeUnit.SECONDS);
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

    @Bean
    public LogExecutionTimeAspect logExecutionTimeAspect() {
        return new LogExecutionTimeAspect();
    }

}
