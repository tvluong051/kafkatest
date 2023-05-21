package com.lightdevel.smg;

import com.lightdevel.smg.models.entities.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumer {
    public static final String TOPIC_NAME = "product-topic";

    @KafkaListener(topics = TOPIC_NAME, groupId = "group1")
    public void consumeName(Product product) {
        log.info("Product [{}] has name {}", product, product.getName());
    }

    @KafkaListener(topics = TOPIC_NAME, groupId = "group2")
    public void consumePrice(Product product) {
        log.info("Product [{}] has price {}", product, product.getPrice());
    }
}
