package com.example.demo.api.dto;

import jakarta.validation.constraints.NotBlank;
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
    private String userName;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;
}
