package nhanph.timekeeping.producer.service;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message, String topic) {
        kafkaTemplate.send(topic, message);
    }
}