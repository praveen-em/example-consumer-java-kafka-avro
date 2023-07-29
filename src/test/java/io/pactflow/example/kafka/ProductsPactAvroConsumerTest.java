package io.pactflow.example.kafka;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Interaction;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.pactflow.example.kafka.model.generated.ProductEvent;
import io.pactflow.example.kafka.service.MessageProcessor;
import org.apache.avro.Schema;
import org.apache.avro.SchemaNormalization;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static au.com.dius.pact.consumer.dsl.PactBuilder.filePath;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(PactConsumerTestExt.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@PactTestFor(providerName = "pactflow-example-provider-java-kafka", providerType = ProviderType.ASYNCH, pactVersion = PactSpecVersion.V4)
public class ProductsPactAvroConsumerTest {
  @Autowired
  MessageProcessor messageProcessor;

  @Pact(consumer = "pactflow-example-consumer-java-kafka")
//   V4Pact createPact(MessagePactBuilder builder) {
//     PactDslJsonBody body = new PactDslJsonBody();
//     body.stringType("name", "product name");
//     body.stringType("type", "product series");
//     body.stringType("id", "5cc989d0-d800-434c-b4bb-b1268499e850");
//     body.stringMatcher("version", "v[a-zA-z0-9]+", "v1");
//     body.stringMatcher("event", "^(CREATED|UPDATED|DELETED)$", "CREATED");
//
//     Map<String, Object> metadata = new HashMap<>();
//     metadata.put("Content-Type", "application/json");
//     metadata.put("kafka_topic", "products-avro");
//
//     return builder.expectsToReceive("a product created event").withMetadata(metadata).withContent(body).toPact();

    V4Pact createPact(PactBuilder builder) {

    Map<String, Object> messageBody = Map.of(
            "message.contents", Map.ofEntries(
                    Map.entry("pact:schema", ProductEvent.SCHEMA$.toString()),
                    Map.entry("pact:record-name", "ProductEvent"),
                    Map.entry("pact:content-type", "application/avro"),
                    Map.entry("name", "notEmpty('product name')"),
                    Map.entry("type", "notEmpty('product series')"),
                    Map.entry("id", "matching(type, '5cc989d0-d800-434c-b4bb-b1268499e850')"),
                    Map.entry("version", "matching(regex, 'v[a-zA-z0-9]+', 'v1')"),
                    Map.entry("event", "matching(regex, '^(CREATED|UPDATED|DELETED)$', 'CREATED')"),
                    Map.entry("createdOn", "matching(date, '2006-01-02', '2023-07-08')"),
                    Map.entry("available", "matching(boolean, true)"),
                    Map.entry("location", Map.of(
                                    "doorNumber", "matching(integer, 29)",
                                    "street", "notEmpty('cross street')",
                                    "postcode", "notEmpty('GU15 7SR')"
                                    )
                    ),
                    Map.entry("otherInfo", Map.of(
                            "key1", "matching(type, 'value1')",
                            "key2", "notEmpty('value2')"
                            )
                    ),
                    Map.entry("otherInfo2",
                            Map.of(
                                    "pact:match", "eachKey(matching(type, 'key1')), eachValue(notEmpty('value11'))"
                            )
//                            Map.of(
//                                    "key1", "matching(equalTo, 'value11')",
//                                    "key2", "notEmpty('value12')"
//                            )
                    ),
                    Map.entry("relatedItems", List.of(
                            "matching(type, 'item1')",
                            "matching(type, 'item2')",
                            "matching(type, 'item3')"
                    )),
                    Map.entry("misc", List.of(
                            "matching(type,10)",
                            "matching(type,11)",
                            "matching(type, 12)"
                    )),
                    Map.entry("misc2", Map.of(
                            "pact:match", "eachValue(matching(type, 100))")
                    ),
//                            List.of(
//                            "matching(equalTo,20)",
//                            "matching(type,21)",
//                            "matching(number, 22)"
//                    )),
                    Map.entry("misc3",
//                            Map.of(
//                            "pact:match", "eachValue(matching($'subProducts'))",
//                            "subProducts", List.of(
//                                    Map.of(
//                                    "name", "notEmpty('name1')",
//                                    "id", "notEmpty(1)"
//                                    ))
//                            )
                            List.of(
                                Map.of(
                                        "name", "matching(type, 'name1')",
                                        "id", "matching(integer, 1)"
                                ),
                                Map.of(
                                        "name", "matching(type, 'name2')",
                                        "id", "matching(integer, 99999)"
                                )
                            )
                    )
            )
    );


//      Map<String, Object> messageBody = Map.of(
//              "message.contents", Map.of(
//                      "pact:avro", filePath("src/main/resources/schemas/avro/ProductEventOld.avsc"),
//                      "pact:record-name", "ProductEvent",
//                      "pact:content-type", "application/avro",
//                      "name", "matching(type, 'product name')",
//                      "type", "matching(date, 'yyyy-MM-dd', '22:04')",
//                      "id", "matching(type, '5cc989d0-d800-434c-b4bb-b1268499e850')",
//                      "version", "matching(regex, 'v[a-zA-z0-9]+', 'v1')",
//                      "event", "matching(regex, '^(CREATED|UPDATED|DELETED)$', 'CREATED')"
//              ));

    return builder
      .usingPlugin("avro")
      .expectsToReceive("a product created event", "core/interaction/message")
      .with(messageBody)
      .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "createPact")
  void test(V4Interaction.AsynchronousMessage message) throws Exception {
     System.out.println("Message received -> " + message.contentsAsString());

     // convert JSON payload from pact file into Avro record.
//      ObjectMapper objectMapper = new ObjectMapper();
//      ProductEvent eventAvro;
//      eventAvro = objectMapper.readValue(message.getContents().getContents().valueAsString(), ProductEvent.class);


    byte[] kafkaBytes = message.contentsAsBytes();

//    ProductEvent eventAvro = ProductEvent.fromByteBuffer(withHeader(kafkaBytes));
    ProductEvent eventAvro = decode(kafkaBytes, ProductEvent.class);
    System.out.println("Converted Avro Record -> " + eventAvro);
    assertDoesNotThrow(() -> {
      messageProcessor.transform(eventAvro).save();
    });
  }

  public static <T> T decode(byte[] avroBytes, Class<T> avroRecordClass) throws Exception{
    BinaryDecoder binaryDecoder = DecoderFactory.get().binaryDecoder(avroBytes, null);
    SpecificDatumReader<T> datumReader = new SpecificDatumReader<>(
            avroRecordClass);
    T avroRecord = datumReader.read(null, binaryDecoder);
    return avroRecord;
  }

  public static ByteBuffer withHeader(byte[] avroBytes) throws Exception{
    byte[] V1_HEADER = new byte[] { (byte) 0xC3, (byte) 0x01 };
    byte[] fp = SchemaNormalization.parsingFingerprint("CRC-64-AVRO", io.pactflow.example.kafka.model.generated.ProductEvent.SCHEMA$);
    ByteBuffer bb = ByteBuffer.allocate(avroBytes.length + V1_HEADER.length + fp.length);
    bb.put(V1_HEADER);
    bb.put(fp);
    bb.put(avroBytes);
    bb.clear();
    return bb;
  }

}