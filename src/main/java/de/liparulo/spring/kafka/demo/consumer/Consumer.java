package de.liparulo.spring.kafka.demo.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

//	@KafkaListener(topics = "hobbit")
//	public void listenGroupFoo(String message) {
//		System.out.println("Received Message in group: " + message);
//	}

//	@KafkaListener(topics = { "streams-wordcount-output" }, groupId = "spring-boot-kafka")
//	public void consume(ConsumerRecord<String, Long> record) {
//		System.out.println("received = " + record.value() + " with key " + record.key());
//	}

}
