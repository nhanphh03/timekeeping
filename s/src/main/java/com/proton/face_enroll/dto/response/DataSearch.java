package com.proton.face_enroll.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataSearch {
    private String people_id;
    private String created_at;
    private String source;
    private double score;
}
