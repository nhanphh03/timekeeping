package nhanph.timekeeping.admin.dto.registerface;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package: proton.face.dto.registerface
 * @author: nhanph
 * @date: 3/3/2025 2025
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
