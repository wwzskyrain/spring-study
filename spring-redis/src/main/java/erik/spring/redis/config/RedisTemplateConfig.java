package erik.spring.redis.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@PropertySource("redis.properties")
public class RedisTemplateConfig {

    @Autowired
    private Environment environment;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();

        jedisConnectionFactory.setHostName(environment.getProperty("redis.host.name"));
        jedisConnectionFactory.setPort(Integer.valueOf(environment.getProperty("redis.port")));
        jedisConnectionFactory.setPassword(environment.getProperty("redis.password"));
        jedisConnectionFactory.setDatabase(Integer.valueOf(environment.getProperty("redis.db.index")));
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate redisConnection(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);

        return redisTemplate;
    }


}

