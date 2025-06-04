package com.example.demo.api.dto;

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
public class UserLoginDto {

    @NotBlank(message = "Tên người dùng không được để trống")
    @Size(min = 8, max = 30, message = "Tên người dùng phải có từ 8 đến 30 ký tự")
    private String userName;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "Mật khẩu phải có ít nhất 8 kí tự")
    private String password;
}
