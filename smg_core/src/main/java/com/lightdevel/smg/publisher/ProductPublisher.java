package com.lightdevel.smg.publisher;

import com.lightdevel.smg.models.entities.Product;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class ProductPublisher {
    private final String topicName;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public ProductPublisher(@Value(value = "${service.kafka" +
            ".topic_name}") String topicName,
                            KafkaTemplate<String, Object> kafkaTemplate) {
        this.topicName = Objects.requireNonNull(topicName);
        this.kafkaTemplate = Objects.requireNonNull(kafkaTemplate);
    }

    public void fanOutProduct(Product product) {
        ListenableFuture<SendResult<String, Object>> futureRes = this.kafkaTemplate.send(topicName, product);

        futureRes.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onSuccess(SendResult<String, Object> result) {
                log.info("Product published: {} with offset: {}", product, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Product publishing failed : {}", product, ex);
            }
        });
    }
}
