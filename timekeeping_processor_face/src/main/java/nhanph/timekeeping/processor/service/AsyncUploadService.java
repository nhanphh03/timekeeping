package nhanph.timekeeping.processor.service;

import nhanph.timekeeping.processor.dto.kafka.KafkaMessage;

public interface AsyncUploadService {
    void uploadImageDetectAsync(KafkaMessage message, String peopleId);
}
