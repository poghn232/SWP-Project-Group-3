package com.example.demo.api.login;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private LoginRepository loginRepository;

    @GetMapping("/login")
    public String showLoginPage(Model model){
        if (!model.containsAttribute("userDto")) {
            model.addAttribute("userDto", new UserLoginDto());
        }
        return "dangnhap&dangky/dangnhap";
    }
    @PostMapping("/login")
    public String loggin(@ModelAttribute("userDto") @Valid UserLoginDto userDto,
                         BindingResult bindingResult
                         ){
        return  "";
    }
}
