package com.greentechpay.paymenthistoryservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopiConfig {

    @Bean
    public NewTopic newTopic() {
        return TopicBuilder.name("Notification-Message")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
