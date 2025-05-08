package nhanph.timekeeping.processor.dto.faceRes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package: nhanph.timekeeping.processor.dto.faceRes
 * @author: nhanph
 * @date: 05/08/2025 2025
 * @Copyright: @nhanph
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveFaceResponse {

    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;
    @JsonProperty("model_name")
    private String modelName;

}
