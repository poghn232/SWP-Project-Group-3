package com.example.demo.api.register;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class RegisterController {

    @Autowired
    private RegisterRepository registerRepository;

    @PostMapping("/register")
    public String getNewUser(@ModelAttribute("registerDto") @Valid UserRegisterDto registerDto,
                             BindingResult bindingResult,
                             @RequestParam String password,
                             @RequestParam String confirmPassword,
                             RedirectAttributes redirectAttributes) {
        if (!password.equals(confirmPassword)) {
           redirectAttributes.addFlashAttribute("error", "Mật khẩu xác nhận không trùng. Vui lòng thử lại");
        }
        return "";
    }
}
