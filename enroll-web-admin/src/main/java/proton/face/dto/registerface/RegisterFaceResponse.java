package proton.face.dto.registerface;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Package: proton.face.dto.registerface
 * @author: nhanph
 * @date: 3/3/2025 2025
 * @Copyright: @nhanph
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterFaceResponse {

    private String status;
    private String message;
    @JsonProperty("people_id")
    private Long peopleId;
    private String source;
    @JsonProperty("meta_data")
    private Object metaData;
}
