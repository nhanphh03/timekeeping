 package nhanph.timekeeping.producer.service;
 import nhanph.timekeeping.common.dto.KafkaMessage;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.kafka.core.KafkaTemplate;
 import org.springframework.kafka.annotation.EnableKafka;
 import org.springframework.stereotype.Component;

 import java.util.UUID;

 /**
  * {@code @Package:} nhanph.timekeeping.producer.service
  * {@code @author:} nhanph
  * {@code @date:} 05/07/2025
  * {@code @Copyright:} @nhanph
  */
 @EnableKafka
 @Component
 public class KafkaProducerService {
     private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

     @Value("${spring.kafka.producer.topic-name}")
     private String topic;

     public KafkaProducerService(KafkaTemplate<String, KafkaMessage> kafkaTemplate) {
         this.kafkaTemplate = kafkaTemplate;
     }

     public void sendMessage(KafkaMessage message) {
         message.setRequestId(UUID.randomUUID().toString());
         kafkaTemplate.send(topic, message);
     }
 }