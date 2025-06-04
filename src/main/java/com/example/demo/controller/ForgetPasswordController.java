//package com.example.demo.controller;
//
//import com.example.demo.api.dto.UserEmailDto;
//import com.example.demo.service.UserService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.parameters.P;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//
//@Controller
//public class ForgetPasswordController {
//
//    private String renderForgetPasswordPage = "dangnhap&dangky/quenMatKhau";
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//
//    @GetMapping("/forgetPassword")
//    public String showForgetPasswordForm(Model model) {
//        if (!model.containsAttribute("emailDto")) {
//            model.addAttribute("emailDto", new UserEmailDto());
//        }
//        return renderForgetPasswordPage;
//    }
//
//    @PostMapping("/forgetPassword")
//    public String sendOTPToUserMail(@ModelAttribute("emailDto") @Valid UserEmailDto userEmailDto,
//                                 BindingResult bindingResult,
//                                 RedirectAttributes redirectAttributes){
//        if(bindingResult.hasErrors()) {
//            return renderForgetPasswordPage;
//        }
//
//        try {
//            userService.OTPEmailSender(userEmailDto.getEmail());
//            redirectAttributes.addFlashAttribute("OTPSent", "OTP đã được gửi đến email của bạn, có thể kiểm tra trong phần tin nhắn rác.");
//            return "redirect:/enterOTP";
//        } catch (IllegalArgumentException e) {
//            if (e.getMessage().contains("Email không tồn tại trong hệ thống")) {
//                bindingResult.rejectValue("email", "notExistEmail", "Email không tồn tại trong hệ thống. Vui lòng xem lại.");
//            }
//        }
//        return renderForgetPasswordPage;
//    }
//
//    @GetMapping("/enterOTP")
//    public String showEnterOTPPage(Model model) {
//
//    }
//
//    @PostMapping("/enterOTP")
//    public String verifyingOTp() {
//
//    }
//
//    @GetMapping("/resetPassword")
//    public String showResetPasswordPage() {
//
//    }
//
//    @PostMapping("resetPassword")
//    public String resetPassword(){
//
//    }
//
//    @GetMapping("/resetPasswordSuccess"){
//
//    }
//}
