package nhanph.timekeeping.processor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nhanph.timekeeping.common.dto.KafkaMessage;
import nhanph.timekeeping.processor.dto.minIO.UploadFileDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncUploadService {

    private final MinIOService minIOService;

    @Async
    public void uploadImageDetectAsync(KafkaMessage message, String peopleId) {
        try {
            LocalDateTime now = LocalDateTime.now();
            String path = String.format("/%d/%02d/%02d/%s",
                    now.getYear(),
                    now.getMonthValue(),
                    now.getDayOfMonth(),
                    peopleId
            );
            UploadFileDTO dto = UploadFileDTO.builder()
                    .base64Img(message.getBase64Image())
                    .fileUrl(path)
                    .fileName(message.getRequestId() + ".jpg")
                    .build();
            minIOService.uploadImageDetection(dto);
        } catch (Exception e) {
            log.error("Upload failed for requestId: {}", message.getRequestId(), e);
        }
    }
}

