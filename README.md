# Product Server
Product API server supports Product record creation, store in datastore and notify consumer about the record creation

## How to build and start it
Please follow these instructions to build the project and start server
### Prerequisites
To build and run server, we need Apache Maven and Java. What I used in my PC is Maven 3.6 and Java 8. Later version should work as well as most of the code is retrocompatible. 
- Install Maven https://maven.apache.org/install.html
- Install JDK 8 https://www.oracle.com/ie/java/technologies/javase/jdk8-archive-downloads.html
### Build server

1. Build core API server
```
cd <ROOT_PROJECT>/smg_core
mvn clean install
// For integration tests: mvn clean install -P integration
``` 
2. Build dummy consumer
```
cd <ROOT_PROJECT>/smg_consumer
mvn clean install
```
3. Run the whole stack
```
docker compose up -d
```
The API server will be available at `localhost` port `8080`. Swagger docu is available at `http://localhost:8080/swagger-ui.html`

### Manual test
1. Use Swagger to create new product
2. Verify that it is stored to DB
3. Check consumer log (should be in `dummylog/consumer.log`) and verify that new product creation is received
```
2023-05-21 21:12:04.877  INFO 1 --- [org.springframework.kafka.KafkaListenerEndpointContainer#1-0-C-1] com.lightdevel.smg.KafkaConsumer         : Product [Product(id=2, name=testProductViet, price=51.15)] has price 51.15
2023-05-21 21:12:04.877  INFO 1 --- [org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] com.lightdevel.smg.KafkaConsumer         : Product [Product(id=2, name=testProductViet, price=51.15)] has name testProductViet
```

## Approach
Server allows to create product through Rest API. It will then fanout the create through messaging system (here Kafka)

### Request handler
At this point, there are 2 options:
#### Pull on request (implemented)
Object created is published to messaging topic. Multiple consumers could subscribe to the topic and pull new value when it comes to the queue
**Pros**: No need to know in advance who will subscribe to get message.
**Cons**: Pull on request could use resource for nothing when pull but no new message available
#### Push on change
Object created is published to messaging topic. Brokers push it to different subscribers of the topic.

To improve we could think about a mechanism to select the mode (push or pull) based on some config.

## Some possible improvement
1. More unit tests, integration tests
2. Support both push on change and pull on request to get best performance
3. Add cache-through before DB in case DB could be accessed by others component
