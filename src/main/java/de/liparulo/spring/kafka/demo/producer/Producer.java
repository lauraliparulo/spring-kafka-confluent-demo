package de.liparulo.spring.kafka.demo.producer;

import java.time.Duration;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Component
public class Producer {
  
  @Autowired
  private KafkaTemplate<Integer, String> template;

  Faker faker;

  @EventListener(ApplicationStartedEvent.class)
  public void generate() {

    faker = Faker.instance();
    final Flux<Long> interval = Flux.interval(Duration.ofMillis(1_000));

    final Flux<String> quotes = Flux.fromStream(Stream.generate(() -> faker.hobbit().quote()));

    Flux.zip(interval, quotes)
        .map(it -> template.send("hobbit", faker.random().nextInt(42), it.getT2())).blockLast();
  }

}

