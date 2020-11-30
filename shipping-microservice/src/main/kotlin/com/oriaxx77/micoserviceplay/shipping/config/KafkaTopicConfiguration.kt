package com.oriaxx77.micoserviceplay.shipping.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder


@Configuration
class KafkaTopicConfiguration
{
    /**
     * This will set the 'orders' topic's partition to N
     */
    @Bean
    fun ordersTopic(): NewTopic {
        return TopicBuilder.name("orders")
                .partitions(1)
                .build()
    }
}