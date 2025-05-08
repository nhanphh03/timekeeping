package nhanph.timekeeping.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaMessage {

    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("base64_image")
    private String base64Image;

    @JsonProperty("time_request")
    private String timeRequest;

    @JsonProperty("camera_code")
    private String cameraCode;
}

