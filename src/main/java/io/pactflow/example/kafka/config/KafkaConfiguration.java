package io.pactflow.example.kafka.config;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import io.pactflow.example.kafka.model.generated.ProductEventAvro;

@EnableKafka
@Configuration
public class KafkaConfiguration {

  // @Bean
  // public ConsumerFactory<String, ProductEvent> productEventConsumerFactory(KafkaProperties kafkaProperties) {
  //   return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties());
  // }

  // @Bean
  // public ConcurrentKafkaListenerContainerFactory<String, ProductEvent> kafkaListenerContainerFactory(KafkaProperties kafkaProperties) {
  //   ConcurrentKafkaListenerContainerFactory<String, ProductEvent> factory = new ConcurrentKafkaListenerContainerFactory<String, ProductEvent>();
  //   factory.setConsumerFactory(productEventConsumerFactory(kafkaProperties));
  //   return factory;
  // }

  @Bean
  public ConsumerFactory<String, String> productConsumerFactory(KafkaProperties kafkaProperties) {
    return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties());
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerStringContainerFactory(KafkaProperties kafkaProperties) {
    ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
    factory.setConsumerFactory(productConsumerFactory(kafkaProperties));
    return factory;
  }

  @Bean
  public ConsumerFactory<String, ProductEventAvro> productEventAvroConsumerFactory(KafkaProperties kafkaProperties) {
    return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties());
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, ProductEventAvro> kafkaListenerContainerFactory(KafkaProperties kafkaProperties) {
    ConcurrentKafkaListenerContainerFactory<String, ProductEventAvro> factory = new ConcurrentKafkaListenerContainerFactory<String, ProductEventAvro>();
    factory.setConsumerFactory(productEventAvroConsumerFactory(kafkaProperties));
    return factory;
  }


}