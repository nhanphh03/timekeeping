package nhanph.timekeeping.processor.controller;

import lombok.RequiredArgsConstructor;
import nhanph.timekeeping.processor.dto.detect.DetectionDTO;
import nhanph.timekeeping.processor.service.DetectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/api/detection"})
@RequiredArgsConstructor
public class DetectionController {

    private final DetectionService detectionService;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/all")
    public ResponseEntity<List<DetectionDTO>> getAllDetection() {
        return ResponseEntity.ok(detectionService.getAllDetection());
    }

    @GetMapping("/as")
    public List<DetectionDTO> as() {
        messagingTemplate.convertAndSend("/topic/timekeeping-detector", detectionService.getAllDetection());
        return detectionService.getAllDetection();
    }

    @GetMapping("/first")
    public ResponseEntity<DetectionDTO> getFirstDetection() {
        return ResponseEntity.ok(detectionService.firstDetection());
    }

}
