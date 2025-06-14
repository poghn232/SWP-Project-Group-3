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
public class UserLoginDto {

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 8, max = 30, message = "Username must have from 8 to 30 characters")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;
}
