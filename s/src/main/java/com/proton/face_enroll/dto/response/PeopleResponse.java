package com.proton.face_enroll.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeopleResponse {
    private String peopleId;
    private String fullName;
    private String mobilePhone;
}
