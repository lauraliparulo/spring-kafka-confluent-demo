package de.liparulo.spring.kafka.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import io.confluent.developer.avro.Hobbit;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;

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
	  
	  @Value("${spring.kafka.properties.schema.registry.url}") 
	    private String schemaRegistryUrl;

	
	  @Value("${spring.kafka.properties.basic.auth.credentials.source}") 
	    private String basicAuthCredentialSource;
	  
	  @Value("${spring.kafka.properties.basic.auth.user.info}") 
	    private String userInfo;

	  @Bean
	  public ConsumerFactory<Integer, Hobbit> consumerFactory() {
	      Map<String, Object> configProps = new HashMap<>();
	      configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
	      configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, 	IntegerDeserializer.class);
	      configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
//	      configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
	      configProps.put("sasl.mechanism", saslMechanism);
	      configProps.put("sasl.jaas.config", salsJaasConfig);
	      configProps.put("security.protocol", securityProtocol);
	      configProps.put("schema.registry.url", schemaRegistryUrl);    
	      configProps.put("basic.auth.credentials.source", basicAuthCredentialSource);    
	      configProps.put("basic.auth.user.info", userInfo);       
	      
	      return new DefaultKafkaConsumerFactory<>(configProps);
	  }
	  

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Integer, Hobbit> 
      kafkaListenerContainerFactory() {
   
        ConcurrentKafkaListenerContainerFactory<Integer, Hobbit> factory =
          new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
