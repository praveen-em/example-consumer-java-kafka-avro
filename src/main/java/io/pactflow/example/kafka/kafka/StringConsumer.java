package io.pactflow.example.kafka.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import io.pactflow.example.kafka.Application;
import io.pactflow.example.kafka.service.MessageProcessor;


// @Service
// public class StringConsumer {
// 	public static Logger logger = LoggerFactory.getLogger(Application.class);

// 	@Autowired
// 	private MessageProcessor messageProcessor;

// 	@KafkaListener(topics = "products-string", groupId = "products-string-group", clientIdPrefix = "string", containerFactory = "kafkaListenerStringContainerFactory")
// 	public void listen(ConsumerRecord<String, String> record) throws Exception {
// 		messageProcessor.transform(record.value()).save();
// 	}
// }