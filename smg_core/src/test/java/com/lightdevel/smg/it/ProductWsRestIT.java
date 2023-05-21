package com.lightdevel.smg.it;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lightdevel.smg.models.entities.Product;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = {"test-topic"}, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
@Sql({"classpath:it/schema.sql"})
public class ProductWsRestIT extends WsRestITBase {
    private static final String PRODUCT_BASE_URL = "/api/v0/products/product";
    private static final String COUNT_PRODUCT_QUERY = "select count(*) from product";
    private static String TOPIC_NAME = "test-topic";
    private Consumer<String, String> consumerServiceTest;

    @Before
    public void setUp() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "groupId");
        DefaultKafkaConsumerFactory<String, String> consumer = new DefaultKafkaConsumerFactory<>(props);

        consumerServiceTest = consumer.createConsumer();
        consumerServiceTest.subscribe(Collections.singletonList(TOPIC_NAME));
    }

    @Test
    public void testCreateProduct() throws JsonProcessingException {
        assertEquals(Integer.valueOf(0), jdbcTemplate.queryForObject(COUNT_PRODUCT_QUERY, Integer.class));
        String productName = "productName";
        String body = "{\n" +
                "  \"name\": \"" + productName + "\",\n" +
                "  \"price\": 10.05\n" +
                "}";

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(body)
                .when()
                    .post(getPath(PRODUCT_BASE_URL))
                .then()
                    .statusCode(HttpStatus.OK.value())
        ;
        ConsumerRecord<String, String> received =  KafkaTestUtils.getSingleRecord(consumerServiceTest, TOPIC_NAME);

        Product actual = new ObjectMapper().readValue(received.value(), Product.class);

        assertEquals(productName, actual.getName());
        assertEquals(new BigDecimal("10.05"), actual.getPrice());
        assertEquals(Integer.valueOf(1), jdbcTemplate.queryForObject(COUNT_PRODUCT_QUERY, Integer.class));
    }
}
