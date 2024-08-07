package de.liparulo.spring.kafka.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
	
	  @Value("${spring.kafka.bootstrap-servers}") 
	  private String bootstrapAddress;

	  @Value("${spring.kafka.properties.sasl.mechanism}") 
	    private String saslMechanism;
	  
	  
	  @Value("${spring.kafka.properties.sasl.jaas.config}") 
	    private String salsJaasConfig;
	
	  @Value("${spring.kafka.properties.security.protocol}") 
	    private String securityProtocol;
	  
	  @Value("${spring.kafka.producer.client-id}") 
	    private String groupId;

//    @Bean
//    public ConsumerFactory<String, String> consumerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(
//          ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, 
//          bootstrapAddress);
//        props.put(
//          ConsumerConfig.GROUP_ID_CONFIG, 
//          groupId);
//        props.put(
//          ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, 
//          StringDeserializer.class);
//        props.put(
//          ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, 
//          StringDeserializer.class);
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
    
	  @Bean
	  public ConsumerFactory<Integer, String> consumerFactory() {
	      Map<String, Object> configProps = new HashMap<>();
	      configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
	      configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, 	IntegerDeserializer.class);
	      configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	      configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
	      configProps.put("sasl.mechanism", saslMechanism);
	      configProps.put("sasl.jaas.config", salsJaasConfig);
	      configProps.put("security.protocol", securityProtocol);
	      return new DefaultKafkaConsumerFactory<>(configProps);
	  }
	  

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Integer, String> 
      kafkaListenerContainerFactory() {
   
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
          new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
