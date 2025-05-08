package com.proton.face_enroll.dto.request;

import com.proton.face_enroll.utils.UploadedFile;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class EnrollCreateHaveFileWebReq implements Serializable {
    private UploadedFile imagePath;
    private String peopleId;
}
