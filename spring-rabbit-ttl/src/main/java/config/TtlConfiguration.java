package config;

import com.ximalaya.business.common.lib.mq.TTLMessageQueueConfiguration;
import config.listener.MyListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author erik.wang
 * @date 2020-06-10 18:03
 */
@Configuration
@ComponentScan(basePackages = "config.listener")
public class TtlConfiguration {


    String mainstayRegistryAddress = "172.29.0.23:2181,172.29.0.20:2181,172.29.0.19:2181,172.29.0.14:2181,172.29.0.18:2181";

    @Bean(value = "ttlConnectionFactory")
    public ConnectionFactory getConnectionFactory() {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();

        connectionFactory.setHost("192.168.3.161");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setChannelCacheSize(50);
        return connectionFactory;

    }

    @Bean
    public TTLMessageQueueConfiguration vipPurchaseMessageTTLConfiguration(@Qualifier("ttlConnectionFactory") ConnectionFactory connectionFactory,
                                                                           MyListener myListener) {

        TTLMessageQueueConfiguration albumSuccessOrderQueueConfiguration = buildTTLMessageQueueConfigurationWithBaseConfig();

        albumSuccessOrderQueueConfiguration.setBusinessName("erik.test.ttl.retry");
        albumSuccessOrderQueueConfiguration.setRoutingKey("");
        albumSuccessOrderQueueConfiguration.setBindToExchange("erik.test.ttl.retry.exchange");
        albumSuccessOrderQueueConfiguration.setBindToExchangeRoutingKey("");
        albumSuccessOrderQueueConfiguration.setConnectionFactory(connectionFactory);

        albumSuccessOrderQueueConfiguration.setMessageListener(myListener);
        return albumSuccessOrderQueueConfiguration;
    }

    private TTLMessageQueueConfiguration buildTTLMessageQueueConfigurationWithBaseConfig() {

        TTLMessageQueueConfiguration queueConfiguration = new TTLMessageQueueConfiguration();

        queueConfiguration.setZkConnectString(mainstayRegistryAddress);
        queueConfiguration.setRetryInitialInterval(1);
        queueConfiguration.setRetryMaxInterval(20);
        queueConfiguration.setRetryMaxAttempts(1);

        // 消息在retry queue中待的时间,单位毫秒，这里配置的是5秒
        queueConfiguration.setRetryQueueTTL(5000);
        //进死信队列前重试5次
        queueConfiguration.setRetryBeforeToDead(5);

        queueConfiguration.setPrefetchCount(10);
        queueConfiguration.setConcurrentConsumers(5);
        queueConfiguration.setMaxConcurrentConsumers(5);
        return queueConfiguration;
    }

}
