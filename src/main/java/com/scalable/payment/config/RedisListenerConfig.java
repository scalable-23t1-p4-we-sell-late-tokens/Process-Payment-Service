package com.scalable.payment.config;

import com.scalable.payment.component.redis.RedisProgressChannel;
import com.scalable.payment.component.redis.RedisRollbackChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisListenerConfig {

    @Bean
    public RedisMessageListenerContainer messageListenerContainer(
            RedisConnectionFactory connectionFactory,
            RedisProgressChannel progressChannel,
            RedisRollbackChannel rollbackChannel) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        // Listen to order to get progressive message
        // Listen to inventory to get rollback message
        container.addMessageListener(progressChannel, new ChannelTopic("orderToPayment"));
        container.addMessageListener(rollbackChannel, new ChannelTopic("inventoryToPayment"));

        return container;
    }
}
