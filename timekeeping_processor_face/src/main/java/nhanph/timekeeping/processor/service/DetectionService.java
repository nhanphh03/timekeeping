package nhanph.timekeeping.processor.service;

import nhanph.timekeeping.processor.dto.detect.DetectionDTO;
import nhanph.timekeeping.processor.entity.Detection;

import java.util.List;

public interface DetectionService {

    List<DetectionDTO> getAllDetection();

    DetectionDTO firstDetection();

    void saveDetection(Detection detection);
}
