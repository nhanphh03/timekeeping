 package nhanph.timekeeping.producer.controller;

 import nhanph.timekeeping.common.dto.KafkaMessage;
 import nhanph.timekeeping.producer.service.KafkaProducerService;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RestController;

 /**
  * {@code @Package:} nhanph.timekeeping.producer.controller
  * {@code @author:} nhanph
  * {@code @date:} 05/07/2025
  * {@code @Copyright:} @nhanph
  */

 @RestController
 @RequestMapping("/procedure")
 public class KafkaController {

     private final KafkaProducerService producerService;

     public KafkaController(KafkaProducerService producerService) {
         this.producerService = producerService;
     }

     @PostMapping("/send-detector")
     public void sendMessage(@RequestBody KafkaMessage request) {
         producerService.sendMessage(request);
     }
 }
