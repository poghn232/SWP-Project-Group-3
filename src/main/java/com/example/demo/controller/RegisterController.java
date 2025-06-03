package com.example.demo.controller;

import com.example.demo.api.register.UserRegisterDto;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

    //render page
    private final String renderRegisterPage = "dangnhap&dangky/dangky";
    private final String registerDto = "registerDto";
    private final String successRegistered = "dangkyThanhcong";

    @Autowired
    private UserService userService;

    // create new model when the web is loaded the first time
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        if (!model.containsAttribute(registerDto)) {
            model.addAttribute(registerDto, new UserRegisterDto());
        }
        return renderRegisterPage;
    }

    @PostMapping("/register")
    public String getNewUser(@ModelAttribute("registerDto") @Valid UserRegisterDto registerDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return renderRegisterPage;
        }

        if (!registerDto.isPasswordMatched()) {
            bindingResult.rejectValue("confirmPassword", "passwordMismatch", "Mật khẩu xác nhận không khớp");
            return renderRegisterPage;
        }

        //call service
        try {
            userService.registerNewUser(registerDto);
            redirectAttributes.addFlashAttribute(successRegistered, "Đăng ký thành công!");
            return "redirect:/dangky-thanhcong";
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("Email đã tồn tại")) {
                bindingResult.rejectValue("email", "existedEmail", e.getMessage());
            } else if (e.getMessage().contains("Tên tài khoản đã tồn tại")) {
                bindingResult.rejectValue("username", "existedUsername", e.getMessage());
            } else if (e.getMessage().contains("Số điện thoại đã tồn tại")) {
                bindingResult.rejectValue("phone", "existedPhoneNumber", e.getMessage());
            } else {
                bindingResult.reject("registerationUnexpectedError", e.getMessage());
            }
            return renderRegisterPage;
        }
    }

    @GetMapping("/dangky-thanhcong")
    public String registrationSuccess(Model model) {
        if (!model.containsAttribute(successRegistered)) {
            model.addAttribute(successRegistered, "Đăng ký thành công!");
        }
        return "dangnhap&dangky/dangky-thanhcong";
    }
}
