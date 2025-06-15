package com.example.demo.api.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserOTPDto {

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email is not in the right form")
    private String email;

    @NotBlank(message = "OTP code cannot be empty")
    @Pattern(regexp = "^[0-9]{6}$", message = "OTP code can only have numbers")
    private String otp;
}
