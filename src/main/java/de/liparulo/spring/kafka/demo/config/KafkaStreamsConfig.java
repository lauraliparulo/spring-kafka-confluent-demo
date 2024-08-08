package de.liparulo.spring.kafka.demo.config;

import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG;

import java.time.Duration;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
@EnableKafka
@EnableKafkaStreams 
public class KafkaStreamsConfig {

	  @Value("${spring.kafka.bootstrap-servers}") 
	  private String bootstrapAddress;

	  @Value("${spring.kafka.properties.sasl.mechanism}") 
	    private String saslMechanism;
	  
	  
	  @Value("${spring.kafka.properties.sasl.jaas.config}") 
	    private String salsJaasConfig;
	
	  @Value("${spring.kafka.properties.security.protocol}") 
	    private String securityProtocol;
	  
	  @Value("${spring.kafka.streams.application-id}") 
	    private String applicationId;
	 
	  
    @Bean(name = 
    KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfigs() {
        return new KafkaStreamsConfiguration(Map.of(
        		APPLICATION_ID_CONFIG, applicationId,
            BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress,
            DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass().getName(),
            DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName(),
            DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class.getName(),
           StreamsConfig.SECURITY_PROTOCOL_CONFIG, securityProtocol,
        	SaslConfigs.SASL_MECHANISM, saslMechanism,
        	SaslConfigs.SASL_JAAS_CONFIG, salsJaasConfig
        ));
    }

    @Bean 
    public KStream <Integer, String> kStream (StreamsBuilder kStreamBuilder) {
		KStream<Integer, String> stream = kStreamBuilder.stream("hobbit");
		stream
    		.mapValues((ValueMapper<String, String>) String::toUpperCase)
   	 	.groupByKey()
    		.windowedBy(TimeWindows.of(Duration.ofMillis(1000)))
    		.reduce((String value1, String value2) -> value1 + value2, Named.as("windowStore"))
    		.toStream()
    		.map((windowedId, value) -> new KeyValue <>(windowedId.key(), value))
    		.filter((i, s) -> s.length() > 40)
    		.to("hobbit2");
   		stream.print(Printed.toSysOut());
		return stream;
}

}
