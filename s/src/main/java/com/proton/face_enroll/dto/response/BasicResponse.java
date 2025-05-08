package com.proton.face_enroll.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse {
    private Integer code;
    private String status;
    private String message;

    public static ResponseEntity<?> RESPONSE_SUCCESS(Integer code, String message) {
        return ResponseEntity.ok(new BasicResponse(code,"SUCCESS", message));
    }

    public static ResponseEntity<?> RESPONSE_ERROR(Integer code,String message) {
        return ResponseEntity.ok(new BasicResponse(code,"ERROR", message));
    }

}
