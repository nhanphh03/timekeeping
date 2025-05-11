package nhanph.timekeeping.processor.dto.detect;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetectionDTO {
    private String id;
    private String customerCode;
    private String imageSrc;
    private String description;
    private String status;
    private String name;
    private String timeIn;
    private String timeOut;
    private String title;
    private String index;
}
