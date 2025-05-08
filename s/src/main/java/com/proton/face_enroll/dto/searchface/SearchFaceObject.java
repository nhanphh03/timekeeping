package com.proton.face_enroll.dto.searchface;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package: proton.face.dto.searchface
 * @author: nhanph
 * @date: 3/3/2025 2025
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
