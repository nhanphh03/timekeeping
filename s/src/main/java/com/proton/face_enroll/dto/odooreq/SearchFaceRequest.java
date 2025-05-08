package com.proton.face_enroll.dto.odooreq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code @Package:} com.proton.face_enroll.dto.odoo
 * {@code @author:} nhanph
 * {@code @date:} 3/18/2025 2025
 * {@code @Copyright:} @nhanph
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchFaceRequest {
    @JsonProperty("image_path")
    private String imagePath;
}
