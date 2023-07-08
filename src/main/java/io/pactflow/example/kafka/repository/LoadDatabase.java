package io.pactflow.example.kafka.repository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.pactflow.example.kafka.model.Product;

@Configuration
@Slf4j
class LoadDatabase {

  @Bean
  CommandLineRunner initDatabase(ProductRepository repository) {
    return args -> {
      log.info("Preloading " + repository.save(new Product("1", "Burger", "Aussie with avocade, egg, bacon and beetroot", "v2", "CREATED")));
      log.info("Preloading " + repository.save(new Product("2" , "Chips", "Sweet potato fries", "v1", "UPDATED")));
    };
  }
}