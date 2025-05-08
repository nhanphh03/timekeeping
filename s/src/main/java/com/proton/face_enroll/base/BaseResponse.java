package com.proton.face_enroll.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * {@code @Package:} com.proton.face_enroll.controller
 * {@code @author:} nhanph
 * {@code @date:} 3/18/2025 2025
 * {@code @Copyright:} @nhanph
 */

@Data
public abstract class BaseResponse {
    @JsonProperty("response_code")
    private String resCode;
    @JsonProperty("response_message")
    private String resMsg;
}
