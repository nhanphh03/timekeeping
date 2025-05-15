package nhanph.timekeeping.processor.service.impl;

import lombok.RequiredArgsConstructor;
import nhanph.timekeeping.processor.dto.detect.DetectionDTO;
import nhanph.timekeeping.processor.entity.Detection;
import nhanph.timekeeping.processor.repository.DetectionRepository;
import nhanph.timekeeping.processor.service.DetectionService;
import nhanph.timekeeping.processor.util.Constants;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DetectionServiceImpl implements DetectionService {

    private static final String BASE_QUERY = """
        SELECT c.id,
               c.customer_code AS customerCode,
               d.image_path AS imageSrc,
               c.full_name AS name,
               d.first_time_check_in AS timeIn,
               d.last_time_check_in AS timeOut,
               cg.code AS title
        FROM customer c
        JOIN detection d ON d.customer_code = c.customer_code
        JOIN customer_group cg ON cg.id = c.group_id
        ORDER BY d.last_time_check_in DESC
        """;

    private final DetectionRepository detectionRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<DetectionDTO> getAllDetection() {
        List<DetectionDTO> detections = jdbcTemplate.query(BASE_QUERY,
                new BeanPropertyRowMapper<>(DetectionDTO.class));

        if (detections.isEmpty()) {
            return Collections.emptyList();
        }

        processDetectionList(detections);
        return detections;
    }

    @Override
    public DetectionDTO firstDetection() {
        String query = BASE_QUERY + " LIMIT 1";
        DetectionDTO dto = jdbcTemplate.queryForObject(query,
                new BeanPropertyRowMapper<>(DetectionDTO.class));

        if (dto == null) {
            return null;
        }

        processDetectionStatus(dto);
        dto.setIndex(formatIndex(1));
        return dto;
    }

    @Override
    public void saveDetection(Detection detection) {
        detectionRepository.save(detection);
    }

    private void processDetectionList(List<DetectionDTO> list) {
        for (int i = 0; i < list.size(); i++) {
            DetectionDTO item = list.get(i);
            processDetectionStatus(item);
            item.setIndex(formatIndex(i + 1 ));
        }
    }

    private String formatIndex(int number) {
        return String.format("%02d", number);
    }

    private void processDetectionStatus(DetectionDTO item) {
        boolean hasTimeIn = item.getTimeIn() != null;
        boolean hasTimeOut = item.getTimeOut() != null;

        if (hasTimeIn && !hasTimeOut) {
            item.setStatus(Constants.STATUS_WELCOME);
            item.setDescription(Constants.DESC_WELCOME);
        } else if (hasTimeIn) {
            Timestamp timeIn = Timestamp.valueOf(item.getTimeIn());
            Timestamp timeOut = Timestamp.valueOf(item.getTimeOut());
            long diff = timeOut.getTime() - timeIn.getTime();
            if (Math.abs(diff) > Constants.TIME_WORK) {
                item.setStatus(Constants.STATUS_ACTIVE);
                item.setDescription(Constants.DESC_DONE);
            } else {
                item.setStatus(Constants.STATUS_INACTIVE);
                item.setDescription(Constants.DESC_INSUFFICIENT);
            }
            item.setTimeIn(item.getTimeIn().split(" ")[1]);
            item.setTimeOut(item.getTimeOut().split(" ")[1]);
        }
        item.setImageSrc(Constants.MINIO_SERVICE.urlMinio + item.getImageSrc());
    }
}
