package nhanph.timekeeping.producer.controller;

import nhanph.timekeeping.producer.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code @Package:} nhanph.timekeeping.producer.controller
 * {@code @author:} nhanph
 * {@code @date:} 05/07/2025
 * {@code @Copyright:} @nhanph
 */

@RestController
public class KafkaController {

    private final KafkaProducerService producerService;

    @Autowired
    public KafkaController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/sendMessage")
    public String sendMessage(@RequestBody String message) {
        producerService.sendMessage(message, "timekeeping-topic");
        return "Message sent to Kafka: " + message;
    }
}
