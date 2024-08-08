package de.liparulo.spring.kafka.demo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

	  @Bean
	  NewTopic hobbit() {
	    return TopicBuilder.name("hobbit").partitions(15).replicas(3).build();
	  }
	
	  @Bean
	  NewTopic hobbit2() {
	    return TopicBuilder.name("hobbit2").partitions(15).replicas(3).build();
	  }
	  
	  @Bean
	  NewTopic counts() {
	    return TopicBuilder.name("streams-wordcount-output").partitions(6).replicas(3).build();
	  }	
}
