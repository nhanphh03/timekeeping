package com.proton.face_enroll.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindPeopleByIdReq {
    String peopleId;
    String phoneNumber;
    String name;
}
