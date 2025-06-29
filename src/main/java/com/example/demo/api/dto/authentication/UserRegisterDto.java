package com.example.demo.api.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRegisterDto {

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 8, max = 30, message = "Username must have from 8 to 30 characters")
    private String userName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email is not the the right form")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "Phone number cannot be empty")
    @Size(min = 9, max = 10, message = "Phone number must have 9 to 10 characters")
    private String phone;

    @NotBlank(message = "Password cannot be empty!")
    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;

    @NotBlank(message = "Confirm password cannot be empty!")
    private String confirmPassword;

    public boolean isPasswordMatched() {
        return password.equals(confirmPassword);
    }
}
