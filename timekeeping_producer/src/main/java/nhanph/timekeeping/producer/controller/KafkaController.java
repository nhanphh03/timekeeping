 package nhanph.timekeeping.producer.controller;

 import nhanph.timekeeping.common.dto.KafkaMessage;
 import nhanph.timekeeping.producer.service.KafkaProducerService;
 import org.springframework.web.bind.annotation.*;

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
     @CrossOrigin(origins = "http://192.168.1.9:2003")
     public void sendMessage(@RequestBody KafkaMessage request) {
         producerService.sendMessage(request);
     }
 }
