package com.proton.face_enroll.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnrollReportReq {
    String peopleId;
    Integer groupId;
    String fromDate;
    String toDate;
    boolean isMorningLate;
    boolean noCheckIn;
}
