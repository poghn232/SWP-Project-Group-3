package com.example.demo.api.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEmailDto {

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email is not in the right form")
    private String email;
}
