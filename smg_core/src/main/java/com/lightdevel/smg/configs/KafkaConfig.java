package com.lightdevel.smg.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Value(value = "${service.kafka.topic_name}")
    public String topicName;
    @Value(value = "${service.kafka.partition_count}")
    public Integer partitionCount;
    @Value(value = "${service.kafka.replica_count}")
    public Integer replicaCount;

    @Bean
    public NewTopic productTopic() {
        return TopicBuilder.name(topicName)
                .partitions(partitionCount)
                .replicas(replicaCount)
                .build();
    }
}
