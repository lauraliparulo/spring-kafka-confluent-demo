package de.liparulo.spring.kafka.demo.processor;

import java.util.Arrays;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class WordCountProcessor {
	

	private static final Serde<String> STRING_SERDE = Serdes.String();
	
	private static final Serde<Integer> INTEGER_SERDE = Serdes.Integer();
	
	private static final Serde<Long> LONG_SERDE = Serdes.Long();
	
    @Autowired
    public void process(StreamsBuilder builder) {
	
    	 //high-level DSL to define the transformations
		 
    	//Create a KStream from the input topic using the specified key and value SerDes.
    	 KStream<Integer, String> textLines = builder.stream("hobbit", Consumed.with(INTEGER_SERDE, STRING_SERDE));
    		//	 .peek((key, value) -> System.out.println("Incoming record from Hobbit via Processor - key " + key + " value " + value));;
    	 //Create a KTable by transforming, splitting, grouping, and then counting the data.
		 KTable<String, Long> wordCounts = textLines
				    .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
				    .groupBy((key, value) -> value, Grouped.with(STRING_SERDE, STRING_SERDE))
				    .count(Materialized.as("counts"));
		 
		 		//Materialize the result to an output stream.
				wordCounts.toStream().to("streams-wordcount-output", Produced.with(STRING_SERDE, LONG_SERDE));
      }
  }