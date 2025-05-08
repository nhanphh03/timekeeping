package com.proton.face_enroll.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollReportResponse {

    private String peopleId;
    private String fullName;
    private String checkIn;
    private String checkOut;
    private String imagePath;
    private String enrolledDate;
    private String totalWorkHours;
    private String groupName;
    private String description;
    private String noonTime;
    private String morningLate;
    private String earlyLeave;

    public void setMorningLateV2(String morningLate) {
        this.morningLate = morningLate;
    }
}
