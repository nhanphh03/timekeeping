package nhanph.timekeeping.processor.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nhanph.timekeeping.common.dto.KafkaMessage;
import nhanph.timekeeping.processor.dto.faceReq.SearchFaceRequest;
import nhanph.timekeeping.processor.dto.faceRes.SearchFaceObject;
import nhanph.timekeeping.processor.dto.faceRes.SearchFaceResponse;
import nhanph.timekeeping.processor.entity.Detection;
import nhanph.timekeeping.processor.repository.DetectionRepository;
import nhanph.timekeeping.processor.service.AsyncUploadService;
import nhanph.timekeeping.processor.service.FaceService;
import nhanph.timekeeping.processor.service.RedisService;
import nhanph.timekeeping.processor.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerService {

    private final FaceService faceService;
    private final DetectionRepository detectionRepository;
    private final AsyncUploadService asyncUploadService;
    private final RedisService redisService;

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
        return handleFaceResponse(response.getData().get(0), message);
    }

    private SearchFaceResponse searchFace(String base64Img) {
        SearchFaceRequest request = SearchFaceRequest.builder()
                .image(base64Img)
                .source(Constants.DATA_SOURCE)
                .build();
        return faceService.searchFace(request);
    }

    private String handleFaceResponse(SearchFaceObject faceObject, KafkaMessage message) {
        if (faceObject == null || StringUtils.isEmpty(faceObject.getPeopleId())) return null;

        Double score = faceObject.getScore();
        if (score == null || score < 0 || score > 1) return null;

        String recognitionStatus = determineRecognitionStatus(score);

        asyncUploadService.uploadImageDetectAsync(message, faceObject.getPeopleId());

        LocalDateTime now = LocalDateTime.now();
        String path = String.format("/%d/%02d/%02d/%s/",
                now.getYear(),
                now.getMonthValue(),
                now.getDayOfMonth(),
                faceObject.getPeopleId()
        );
        path = path + "/" +  message.getRequestId() + ".jpg";

        Detection detection = buildDetection(faceObject, message, recognitionStatus, path);
        detectionRepository.save(detection);
        return faceObject.getPeopleId();
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
                .peopleId(searchFaceObject.getPeopleId())
                .createdTime(new Date())
                .capturedTime(message.getTimeRequest())
                .imagePath(filePath)
                .firstTimeCheckIn(getFirstTimeCheckIn(searchFaceObject.getPeopleId(), now))
                .lastTimeCheckIn(now)
                .responseRaw(searchFaceObject.toString())
                .recognitionStatus(recognitionStatus)
                .build();
    }

    private String getFirstTimeCheckIn(String peopleId, String now) {
        Object firstTimeCheckIn = redisService.get("first_" + peopleId);
        if (firstTimeCheckIn == null) {
            firstTimeCheckIn = now;
            redisService.save("first_" + peopleId, firstTimeCheckIn);
        }
        return firstTimeCheckIn.toString();
    }
}

