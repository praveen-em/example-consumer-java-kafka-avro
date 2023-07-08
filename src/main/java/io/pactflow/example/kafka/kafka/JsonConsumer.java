package io.pactflow.example.kafka.kafka;


// @Service
// public class JsonConsumer {
// 	public static Logger logger = LoggerFactory.getLogger(Application.class);

// 	@Autowired	
// 	private MessageProcessor messageProcessor;

// 	@KafkaListener(topics = "products-json", groupId = "products-json-group", clientIdPrefix = "json", containerFactory = "kafkaListenerContainerFactory")
// 	public void listen(ConsumerRecord<String, ProductEvent> record) throws Exception {
// 		messageProcessor.transform(record.value()).save();
// 	}
// }