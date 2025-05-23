package nhanph.timekeeping.processor.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nhanph.timekeeping.common.dto.KafkaMessage;
import nhanph.timekeeping.processor.dto.detect.DetectionDTO;
import nhanph.timekeeping.processor.dto.faceReq.SearchFaceRequest;
import nhanph.timekeeping.processor.dto.faceRes.SearchFaceObject;
import nhanph.timekeeping.processor.dto.faceRes.SearchFaceResponse;
import nhanph.timekeeping.processor.entity.CapturedImages;
import nhanph.timekeeping.processor.entity.Detection;
import nhanph.timekeeping.processor.service.*;
import nhanph.timekeeping.processor.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerService {

    private final FaceService faceService;
    private final DetectionService detectionService;
    private final CapturedImagesService capturedImagesService;
    private final AsyncUploadService asyncUploadService;
    private final RedisService redisService;
    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "timekeeping-detector", groupId = "timekeeping-detector")
    public void consume(KafkaMessage message) {
        try {
            log.info("{} received handle message from kafka", message.getRequestId());
            String result = processFaceDetection(message);
            if (result == null) {
                log.info("{} face not found or error occurred", message.getRequestId());
            } else {
                log.info("{} detect and save timekeeping face success {}", message.getRequestId(), result);
            }
        } catch (Exception e) {
            log.error("Error processing message with requestId: {}", message.getRequestId(), e);
        }
    }

    private String processFaceDetection(KafkaMessage message) {
        SearchFaceResponse response = searchFace(message.getBase64Image());
        if (response == null || !Constants.STATUS_SUCCESS.equals(response.getStatus())) return null;
        if (response.getData() == null || response.getData().isEmpty()) return null;

        LocalDateTime now = LocalDateTime.now();
        String path = String.format("/%d/%02d/%02d/",
                now.getYear(),
                now.getMonthValue(),
                now.getDayOfMonth()
        );
        SearchFaceObject faceObject = response.getData().get(0);
        Double score = faceObject.getScore();
        String recognitionStatus = determineRecognitionStatus(score);

        CapturedImages capturedImages = buildCapturedImages(faceObject, message, path, recognitionStatus);
        capturedImagesService.saveCapturedImage(capturedImages);

        return handleFaceResponse(faceObject, message, recognitionStatus, score);
    }

    private SearchFaceResponse searchFace(String base64Img) {
        SearchFaceRequest request = SearchFaceRequest.builder()
                .image(base64Img)
                .source(Constants.DATA_SOURCE)
                .build();
        return faceService.searchFace(request);
    }

    private String handleFaceResponse(SearchFaceObject faceObject, KafkaMessage message,
                                      String recognitionStatus, Double score ) {
        if (faceObject == null || StringUtils.isEmpty(faceObject.getCustomerId())) return null;

        if (score == null || score < 0 || score > 1) return null;

        asyncUploadService.uploadImageDetectAsync(message, faceObject.getCustomerId());

        LocalDateTime now = LocalDateTime.now();
        String path = String.format("/%d/%02d/%02d/%s/",
                now.getYear(),
                now.getMonthValue(),
                now.getDayOfMonth(),
                faceObject.getCustomerId()
        );

        path = path +  message.getRequestId() + ".jpg";

        log.info("Save captured image to path: {}", path);

        if (compareLastSearchFace(faceObject.getCustomerId(), message.getTimeRequest())) {
            log.info("{}: Skip face {} because it was detected in the last 30 seconds",
                    message.getRequestId(), faceObject.getCustomerId());
            return null;
        }
        Detection detection = buildDetection(faceObject, message, recognitionStatus, path);
        detectionService.saveDetection(detection);
        List<DetectionDTO> detectionDTOS = detectionService.getAllDetection();
        messagingTemplate.convertAndSend("/topic/timekeeping-detector", detectionDTOS);
        return faceObject.getCustomerId();
    }

    private String determineRecognitionStatus(Double score) {
        return (score >= 0.3 && score <= 0.5) ? Constants.RECOGNITION_STATUS_B : Constants.RECOGNITION_STATUS_A;
    }

    private Detection buildDetection(SearchFaceObject searchFaceObject, KafkaMessage message
            , String recognitionStatus, String filePath) {

        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return Detection.builder()
                .cameraCode(message.getCameraCode())
                .searchId(searchFaceObject.getId())
                .score(searchFaceObject.getScore())
                .customerCode(searchFaceObject.getCustomerId())
                .createdTime(new Date())
                .capturedTime(message.getTimeRequest())
                .imagePath(filePath)
                .firstTimeCheckIn(getFirstTimeCheckIn(searchFaceObject.getCustomerId(), now))
                .lastTimeCheckIn(now)
                .responseRaw(searchFaceObject.toString())
                .recognitionStatus(recognitionStatus)
                .build();
    }

    private CapturedImages buildCapturedImages(SearchFaceObject searchFaceObject, KafkaMessage message
            , String path, String recognitionStatus){
        return CapturedImages.builder()
                .imagePath(path)
                .responseRaw(searchFaceObject.toString())
                .searchId(searchFaceObject.getId())
                .capturedTime(message.getTimeRequest())
                .responseTime(null)
                .cameraCode(message.getCameraCode())
                .customerCode(searchFaceObject.getCustomerId())
                .detectedStatus(recognitionStatus)
                .build();
    }

    private String getFirstTimeCheckIn(String customerId, String now) {
        Object firstTimeCheckIn = redisService.get("first_" + customerId);
        if (firstTimeCheckIn == null) {
            firstTimeCheckIn = now;
            redisService.save("first_" + customerId, firstTimeCheckIn);
        }
        return firstTimeCheckIn.toString();
    }


    private boolean compareLastSearchFace(String customerId, String requestTime) {
        if (requestTime == null) {
            return true;
        }

        Object lastTimeCheckIn = redisService.get("last_" + customerId);

        if (lastTimeCheckIn == null) {
            log.info("lastTimeCheckIn is null, save to redis");
            redisService.save("last_" + customerId, requestTime);
            return false;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(requestTime, formatter);
            Timestamp timestamp = Timestamp.valueOf(localDateTime);

            Timestamp timestampLast = Timestamp.valueOf(lastTimeCheckIn.toString());
            long diff = timestamp.getTime() - timestampLast.getTime();

            log.info("lastTimeCheckIn: {}, requestTime: {}", lastTimeCheckIn, requestTime);
            if (Math.abs(diff) < 60000) {
                return true;
            }

            redisService.save("last_" + customerId, requestTime);
            return false;

        } catch (Exception e) {
            log.error("Error comparing lastTimeCheckIn and requestTime {}", e.getMessage());
            return true;
        }
    }
}

