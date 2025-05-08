package com.proton.face_enroll.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code @Package:} com.proton.face_enroll.controller
 * {@code @author:} nhanph
 * {@code @date:} 3/18/2025 2025
 * {@code @Copyright:} @nhanph
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveFaceResponsePy {

    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;
    @JsonProperty("model_name")
    private String modelName;

}
