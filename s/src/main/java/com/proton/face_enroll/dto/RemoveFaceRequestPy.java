package com.proton.face_enroll.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code @Package:} com.proton.face_enroll.controller
 * {@code @author:} nhanph
 * {@code @date:} 3/18/2025 2025
 * {@code @Copyright:} @nhanph
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveFaceRequestPy {

    @JsonProperty("people_id")
    private String peopleId;
    @JsonProperty("source")
    private String source;

}
