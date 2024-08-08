package de.liparulo.spring.kafka.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import io.confluent.developer.avro.Hobbit;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

@Configuration
public class KafkaProducerConfiguration {

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
	  public ProducerFactory<Integer, Hobbit> producerFactory() {
	      Map<String, Object> configProps = new HashMap<>();
	      configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
	      configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
	      configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
	      configProps.put(ProducerConfig.RETRIES_CONFIG, 3);
	      configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
	      configProps.put("sasl.mechanism", saslMechanism);
	      configProps.put("sasl.jaas.config", salsJaasConfig);
	      configProps.put("security.protocol", securityProtocol);
	      configProps.put("schema.registry.url", schemaRegistryUrl);    
	      configProps.put("basic.auth.credentials.source", basicAuthCredentialSource);    
	      configProps.put("basic.auth.user.info", userInfo);    
	        		      		  
	      return new DefaultKafkaProducerFactory<>(configProps);
	  }
	  
	  @Bean
	  public KafkaTemplate<Integer, Hobbit> kafkaTemplate() {
	    return new KafkaTemplate<>(producerFactory());
	  }
	  
}
