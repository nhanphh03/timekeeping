package nhanph.timekeeping.processor.dto.kafka;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Package: nhanph.timekeeping.processor.dto.kafka
 * @author: nhanph
 * @date: 05/08/2025 2025
 * @Copyright: @nhanph
 */

public class ResponseMessage {
    @JsonProperty("base64_image")
    private String base64Image;
    @JsonProperty("time_request")
    private String timeRequest;
    @JsonProperty("camera_code")
    private String cameraCode;
}
