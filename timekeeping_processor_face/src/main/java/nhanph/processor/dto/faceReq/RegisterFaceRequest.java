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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterFaceRequest {

    private String image;

    @JsonProperty("people_id")
    private String peopleId;

    @JsonProperty("created_at")
    private String createdAt;

    private String source;

    @JsonProperty("meta_data")
    private String metaData;

    @JsonProperty("is_live_check")
    private boolean isLiveCheck;

}
