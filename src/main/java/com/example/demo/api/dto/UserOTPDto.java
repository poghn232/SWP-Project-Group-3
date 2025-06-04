package com.example.demo.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserOTPDto {

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Mã OTP không được để trống.")
    @Size(min = 6, max = 6, message = "Mã OTP phải có 6 chữ số.")
    @Pattern(regexp = "^[0-9]{6}$", message = "Mã OTP chỉ chứa chữ số.")
    private String otp;
}
