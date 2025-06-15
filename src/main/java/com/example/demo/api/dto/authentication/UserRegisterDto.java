package com.example.demo.api.dto.authentication;

import jakarta.validation.constraints.Email;
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
public class UserRegisterDto {

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 8, max = 30, message = "Username must have from 8 to 30 characters")
    private String userName;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email is not the the right form")
    private String email;

    @NotBlank(message = "Phone number cannot be empty")
    @Size(min = 10, max = 10, message = "Phone number must have ")
    private String phone;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "Mật khẩu phải có ít nhất 8 kí tự")
    private String password;

    @NotBlank(message = "Xác nhận mật khẩu không được để trống")
    private String confirmPassword;

    public boolean isPasswordMatched() {
        return password.equals(confirmPassword);
    }
}
