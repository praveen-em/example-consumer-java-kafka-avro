package io.pactflow.example.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.pactflow.example.kafka.model.Product;
import io.pactflow.example.kafka.model.generated.ProductEventAvro;
import io.pactflow.example.kafka.repository.ProductRepository;

@Service
public class MessageProcessor {

    public static Logger logger = LoggerFactory.getLogger(MessageProcessor.class);
	private ObjectMapper objectMapper = new ObjectMapper();	
    private Product product;

    @Autowired
    private ProductRepository productRepository;

    public MessageProcessor transform(ProductEventAvro eventAvro) throws Exception {
		this.product = new Product(
            eventAvro.getId().toString(), 
            eventAvro.getName().toString(), 
            eventAvro.getType().toString(), 
            eventAvro.getVersion().toString(), 
            eventAvro.getEvent().toString());
		logger.info("received product event: {}", product);
        return this;
    }

    public void save() {
        this.productRepository.save(this.product);

    }

    
}
