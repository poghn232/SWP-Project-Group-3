package com.example.demo.api.register;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @PostMapping("/register")
    public String getNewUser(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        return "";
    }
}
