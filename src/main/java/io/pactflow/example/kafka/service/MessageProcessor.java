package io.pactflow.example.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.pactflow.example.kafka.model.Product;
import io.pactflow.example.kafka.model.generated.ProductEvent;
import io.pactflow.example.kafka.repository.ProductRepository;

@Service
public class MessageProcessor {

    public static Logger logger = LoggerFactory.getLogger(MessageProcessor.class);
	private ObjectMapper objectMapper = new ObjectMapper();	
    private Product product;

    @Autowired
    private ProductRepository productRepository;

    public MessageProcessor transform(ProductEvent ProductEvent) throws Exception {
		this.product = new Product(
            ProductEvent.getId().toString(), 
            ProductEvent.getName().toString(), 
            ProductEvent.getType().toString(), 
            ProductEvent.getVersion().toString(), 
            ProductEvent.getEvent().toString());
		logger.info("Domain object: {}", product);
        return this;
    }

    public void save() {
        this.productRepository.save(this.product);

    }

    
}
