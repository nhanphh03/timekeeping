package nhanph.timekeeping.processor.dto.kafka;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package: nhanph.timekeeping.processor.dto.kafka
 * @author: nhanph
 * @date: 05/08/2025 2025
 * @Copyright: @nhanph
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {
    @JsonProperty("request_id")
    private String requestId;
    @JsonProperty("file_path")
    private String filePath;
    @JsonProperty("time_request")
    private String timeRequest;
    @JsonProperty("camera_code")
    private String cameraCode;
}
