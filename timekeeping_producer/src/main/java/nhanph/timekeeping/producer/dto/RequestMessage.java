package nhanph.timekeeping.producer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author nhanph
 */
public class RequestMessage {
    @JsonProperty("base64_image")
    private String base64Image;
    @JsonProperty("time_request")
    private String timeRequest;
    @JsonProperty("camera_code")
    private String cameraCode;
}
