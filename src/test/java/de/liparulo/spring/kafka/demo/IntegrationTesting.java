package de.liparulo.spring.kafka.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

//@Testcontainers
//@SpringBootTest(classes = KafkaStreamsApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class IntegrationTesting {

//    @Container
//    private static final KafkaContainer KAFKA = new KafkaContainer(
//      DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));
//
//    private final BlockingQueue<String> output = new LinkedBlockingQueue<>();
//
//    // other test setup
//
//    @Test
//    void givenInputMessages_whenPostToEndpoint_thenWordCountsReceivedOnOutput() throws Exception {
//        postMessage("test message");
//
//        startOutputTopicConsumer();
//
//        // assert correct counts on output topic
//        assertThat(output.poll(2, MINUTES)).isEqualTo("test:1");
//        assertThat(output.poll(2, MINUTES)).isEqualTo("message:1");
//
//        // assert correct count from REST service
//        assertThat(getCountFromRestServiceFor("test")).isEqualTo(1);
//        assertThat(getCountFromRestServiceFor("message")).isEqualTo(1);
//    }
}
