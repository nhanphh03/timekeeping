 package nhanph.timekeeping.producer.service;
 import nhanph.timekeeping.producer.dto.RequestMessage;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.kafka.core.KafkaTemplate;
 import org.springframework.kafka.annotation.EnableKafka;
 import org.springframework.stereotype.Component;

 /**
  * {@code @Package:} nhanph.timekeeping.producer.service
  * {@code @author:} nhanph
  * {@code @date:} 05/07/2025
  * {@code @Copyright:} @nhanph
  */
 @EnableKafka
 @Component
 public class KafkaProducerService {
     private final KafkaTemplate<String, RequestMessage> kafkaTemplate;

     @Value("${spring.kafka.producer.topic-name}")
     private String topic;

     public KafkaProducerService(KafkaTemplate<String, RequestMessage> kafkaTemplate) {
         this.kafkaTemplate = kafkaTemplate;
     }

     public void sendMessage(RequestMessage message) {
         kafkaTemplate.send(topic, message);
     }
 }