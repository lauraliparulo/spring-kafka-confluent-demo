package de.liparulo.spring.kafka.demo.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

//	@KafkaListener(topics = "hobbit", groupId = "foo")
//	public void listenGroupFoo(String message) {
//	    System.out.println("Received Message in group foo: " + message);
//	}
}
