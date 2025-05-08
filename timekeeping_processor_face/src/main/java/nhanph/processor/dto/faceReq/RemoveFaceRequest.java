package nhanph.timekeeping.processor.dto.faceReq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package: nhanph.timekeeping.processor.dto.faceReq
 * @author: nhanph
 * @date: 05/08/2025 2025
 * @Copyright: @nhanph
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveFaceRequest {

    @JsonProperty("people_id")
    private String peopleId;
    @JsonProperty("source")
    private String source;

}
