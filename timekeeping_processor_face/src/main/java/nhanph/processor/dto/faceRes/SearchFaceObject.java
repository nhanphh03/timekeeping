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
public class SearchFaceObject {

    @JsonProperty("_id")
    private String id;

    @JsonProperty("people_id")
    private String peopleId;

    private String source;

    private Double score;

    @JsonProperty("meta_data")
    private Object metaData;

}
