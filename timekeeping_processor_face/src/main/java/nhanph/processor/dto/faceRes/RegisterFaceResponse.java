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
@NoArgsConstructor
@AllArgsConstructor
public class RegisterFaceResponse {

    private String status;
    private String message;
    @JsonProperty("people_id")
    private Long peopleId;
    @JsonProperty("similarity_score")
    private double similarityScore;
    @JsonProperty("old_people_id")
    private String oldPeopleId;
    private String source;
    @JsonProperty("meta_data")
    private Object metaData;
}