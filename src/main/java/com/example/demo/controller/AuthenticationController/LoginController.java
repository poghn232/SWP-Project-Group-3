package com.example.demo.controller.AuthenticationController;

import com.example.demo.api.dto.authentication.UserLoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private final String renderLoginPage = "dangnhap&dangky/dangnhap";

    @GetMapping("/login")
    public String showLoginPage(Model model){
        if (!model.containsAttribute("userDto")) {
            model.addAttribute("userDto", new UserLoginDto());
        }
        return renderLoginPage;
    }
}
