package com.lightdevel.smg;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic productTopic() {
        return TopicBuilder.name(KafkaConsumer.TOPIC_NAME)
                .partitions(2)
                .replicas(1)
                .build();
    }
}
