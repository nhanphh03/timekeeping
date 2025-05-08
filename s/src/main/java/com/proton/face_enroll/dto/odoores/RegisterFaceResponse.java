package com.proton.face_enroll.dto.odoores;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proton.face_enroll.base.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * {@code @Package:} com.proton.face_enroll.dto.odoores
 * {@code @author:} nhanph
 * {@code @date:} 3/18/2025 2025
 * {@code @Copyright:} @nhanph
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterFaceResponse extends BaseResponse {

    @JsonProperty("people_id")
    private String peopleId;

    @JsonProperty("old_people_id")
    private String oldPeopleId;


}