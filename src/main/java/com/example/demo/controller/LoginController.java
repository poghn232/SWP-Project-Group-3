package com.example.demo.controller;

import com.example.demo.api.dto.UserLoginDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    private final String renderLoginPage = "dangnhap&dangky/dangnhap";

    @GetMapping("/login")
    public String showLoginPage(Model model){
        if (!model.containsAttribute("userDto")) {
            model.addAttribute("userDto", new UserLoginDto());
        }
        return renderLoginPage;
    }
}
