package com.example.demo.api.dto.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 8, max = 30, message = "Username must have from 8 to 30 characters")
    private String username;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email is not the the right form")
    private String email;

    @NotBlank(message = "Phone number cannot be empty")
    @Size(min = 9, max = 10, message = "Phone number must have 9 to 10 characters")
    private String phone;
}
