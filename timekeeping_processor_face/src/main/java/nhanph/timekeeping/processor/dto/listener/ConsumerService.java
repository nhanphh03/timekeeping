package nhanph.timekeeping.processor.dto.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nhanph.timekeeping.processor.dto.faceReq.SearchFaceRequest;
import nhanph.timekeeping.processor.dto.faceRes.SearchFaceObject;
import nhanph.timekeeping.processor.dto.faceRes.SearchFaceResponse;
import nhanph.timekeeping.processor.dto.kafka.ResponseMessage;
import nhanph.timekeeping.processor.entity.Detection;
import nhanph.timekeeping.processor.repository.DetectionRepository;
import nhanph.timekeeping.processor.service.FaceService;
import nhanph.timekeeping.processor.service.MinIOService;
import nhanph.timekeeping.processor.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerService {

    private final FaceService faceService;
    private final MinIOService minIOService;
    private final DetectionRepository detectionRepository;

    @KafkaListener(topics = "timekeeping-detector", groupId = "timekeeping-detector")
    public void consume(ResponseMessage message) {
        try {
            log.info("{} received handle message from kafka", message.getRequestId());
            String result = processFaceDetection(message);
            if (result == null) {
                log.info("{} face not found or error occurred", message.getRequestId());
            } else {
                log.info("{} detect and save timekeeping face success {}", message.getRequestId(), result);
            }
        } catch (Exception e) {
            log.error("Error processing message with requestId: {}" , message.getRequestId(), e);
        }
    }

    private String processFaceDetection(ResponseMessage message) {
        String base64Img = getBase64Image(message);
        if (base64Img == null) return null;

        SearchFaceResponse response = searchFace(base64Img);
        if (response == null || !Constants.STATUS_SUCCESS.equals(response.getStatus())) return null;
        if (response.getData() == null || response.getData().isEmpty()) return null;

        return handleFaceResponse(response.getData().get(0), message);
    }

    private String getBase64Image(ResponseMessage message) {
        String base64Img = minIOService.getBase64Image(message.getFilePath());
        if (base64Img == null) {
            log.info("{} Cannot get Base64 Image from file url", message.getRequestId());
        }
        return base64Img;
    }

    private SearchFaceResponse searchFace(String base64Img) {
        SearchFaceRequest request = SearchFaceRequest.builder()
                .image(base64Img)
                .source(Constants.DATA_SOURCE)
                .build();
        return faceService.searchFace(request);
    }

    private String handleFaceResponse(SearchFaceObject searchFaceObject, ResponseMessage message) {
        if (searchFaceObject == null || StringUtils.isEmpty(searchFaceObject.getPeopleId())) {
            return null;
        }

        Double score = searchFaceObject.getScore();
        if (score == null || score < 0 || score > 1) {
            return null;
        }

        String recognitionStatus = determineRecognitionStatus(score);
        Detection detection = buildDetection(searchFaceObject, message, recognitionStatus);
        detectionRepository.save(detection);
        return searchFaceObject.getPeopleId();
    }

    private String determineRecognitionStatus(Double score) {
        return (score >= 0.3 && score <= 0.5) ? Constants.RECOGNITION_STATUS_B : Constants.RECOGNITION_STATUS_A;
    }

    private Detection buildDetection(SearchFaceObject searchFaceObject, ResponseMessage message, String recognitionStatus) {
        return Detection.builder()
                .cameraCode(message.getCameraCode())
                .searchId(searchFaceObject.getId())
                .score(searchFaceObject.getScore())
                .peopleId(searchFaceObject.getPeopleId())
                .createdTime(new Date())
                .capturedTime(message.getTimeRequest())
                .imagePath(message.getFilePath())
                .firstTimeCheckIn("") // TODO: get from redis
                .firstTimeCheckInNoon("") // TODO: get from redis
                .responseRaw(searchFaceObject.toString())
                .recognitionStatus(recognitionStatus)
                .build();
    }
}