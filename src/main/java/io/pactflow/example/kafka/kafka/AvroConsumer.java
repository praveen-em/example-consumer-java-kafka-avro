package io.pactflow.example.kafka.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import io.pactflow.example.kafka.Application;
import io.pactflow.example.kafka.model.generated.ProductEvent;
import io.pactflow.example.kafka.service.MessageProcessor;



@Service
public class AvroConsumer {
	public static Logger logger = LoggerFactory.getLogger(Application.class);
	@Autowired
    private MessageProcessor messageProcessor;

	@KafkaListener(topics = "products-avro-v4", groupId = "products-avro-group-v4", clientIdPrefix = "avro", containerFactory = "kafkaListenerContainerFactory")
	public void listen(ConsumerRecord<String, ProductEvent> record) throws Exception {
		logger.info("Receveid avro record: {}", record.value());
        messageProcessor.transform(record.value()).save();
	}
}