package nhanph.timekeeping.processor.service;


import nhanph.timekeeping.common.dto.KafkaMessage;

public interface AsyncUploadService {
    void uploadImageDetectAsync(KafkaMessage message, String peopleId);
}
