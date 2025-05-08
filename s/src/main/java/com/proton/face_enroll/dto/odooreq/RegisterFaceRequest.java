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
public class RegisterFaceRequest {

    @JsonProperty("people_id")
    private String peopleId;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("mobile_phone")
    private String mobilePhone;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    @JsonProperty("department")
    private String department;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("type")
    private String type;

    @JsonProperty("image_path")
    private String imagePath;
}
