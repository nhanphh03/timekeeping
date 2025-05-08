package com.proton.face_enroll.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterFaceRequest {

    @NotBlank(message = "Id nhân viên không được để trống")
    private String peopleId;

    @Size(min = 3, message = "Họ tên phải có ít nhất 3 ký tự")
    @NotBlank(message = "Họ và tên không được để trống")
    private String fullName;

    @NotBlank(message = "Ngày tháng năm sinh không được để trống")
    private String dateOfBirth;

    @Pattern(regexp = "\\d{10}", message = "Số điện thoại phải đủ 10 số")
    @NotBlank(message = "Số điện thoại không được để trống")
    private String mobilePhone;

    @NotBlank(message = "Giới tính không được để trống")
    private String gender;

    @NotNull(message = "Ảnh không được để trống")
    private String imagePath;
}
