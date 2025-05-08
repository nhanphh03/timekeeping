package com.proton.face_enroll.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

import java.time.Instant;
@Getter
@Setter
public class CapturedImageDetailResponse {
    public Integer id;
    public String pathImage;
    public String capturedTime;
    public Integer peopleId;
    public Integer cameraId;
}