package com.example.demo.api.dto.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResetPasswordDto {

    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;

    @NotBlank(message = "Confirm password cannot be empty")
    private String confirmPassword;
}
