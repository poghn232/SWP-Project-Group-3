package com.example.demo.controller.AuthenticationController;

import com.example.demo.api.dto.authentication.UserEmailDto;
import com.example.demo.api.dto.authentication.UserOTPDto;
import com.example.demo.api.dto.authentication.UserResetPasswordDto;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ForgetPasswordController {

    private final String renderForgetPasswordPage = "dangnhap&dangky/forgetPassword";
    private final String renderEnterOTPPage = "dangnhap&dangky/enterOTP";
    private final String renderResetPasswordPage = "dangnhap&dangky/resetMatKhau";

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @GetMapping("/forgetPassword")
    public String showForgetPasswordForm(Model model) {
        if (!model.containsAttribute("emailDto")) {
            model.addAttribute("emailDto", new UserEmailDto());
        }
        return renderForgetPasswordPage;
    }

    @PostMapping("/forgetPassword")
    public String sendOTPToUserMail(@ModelAttribute("emailDto") @Valid UserEmailDto userEmailDto,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return renderForgetPasswordPage;
        }

        //session timeout
        if (userEmailDto.getEmail() == null) {
            return renderForgetPasswordPage;
        } else {
            try {
                userService.OTPEmailSender(userEmailDto.getEmail());
                redirectAttributes.addFlashAttribute("successMessage", "OTP đã được gửi đến email của bạn.");
                session.setAttribute("emailResetPassword", userEmailDto.getEmail());
                return "redirect:/enterOTP";
            } catch (IllegalArgumentException e) {
                if (e.getMessage().contains("Email không tồn tại")) {
                    bindingResult.rejectValue("email", "notExistEmail", "Email không tồn tại. Vui lòng thử lại.");
                } else {
                    bindingResult.reject("globalError", e.getMessage()); // Lỗi khác (ví dụ: lỗi gửi email)
                }
                return renderForgetPasswordPage;
            }
        }
    }

    @GetMapping("/enterOTP")
    public String showEnterOTPPage(Model model,
                                   RedirectAttributes redirectAttributes) {

        String email = (String) session.getAttribute("emailResetPassword");

        //session timeout or return back to enterOTP after successfully get to reset password page,
        // or trying to direct access from URL
        if (email == null || email.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng yêu cầu mã OTP để xác minh");
            return "redirect:/forgetPassword";
        }
        UserOTPDto otpDto = new UserOTPDto();
        if (!model.containsAttribute("otpDto")) {
            otpDto.setEmail(email);
        } else {
            otpDto = (UserOTPDto) model.getAttribute("otpDto");
            String otpEmail = otpDto.getEmail();
            if (otpEmail == null || otpEmail.isEmpty()) {
                otpDto.setEmail(email);
            }
        }
        model.addAttribute("otpDto", otpDto);
        return renderEnterOTPPage;
    }

    @PostMapping("/enterOTP")
    public String verifyingOTp(@ModelAttribute("otpDto") @Valid UserOTPDto otpDto,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("otpDto", otpDto);
            return renderEnterOTPPage;
        }

        try {
            boolean otpVerifed = userService.verifyOTP(otpDto.getOtp(), otpDto.getEmail());
            if (otpVerifed) {
                redirectAttributes.addFlashAttribute("successMessage", "Xác nhận thành công mã OTP.");
                return "redirect:/resetPassword";
            }
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("Mã OTP không hợp lệ")) {
                bindingResult.rejectValue("otp", "unvalidOTP", "Mã OTP không hợp lệ. Vui lòng thử lại.");
            } else if (e.getMessage().contains("Mã OTP đã hết hạn")) {
                bindingResult.rejectValue("otp", "expiredOTP", "Mã OTP đã hết hạn. Vui lòng thử lại");
            } else {
                bindingResult.reject("globalError", e.getMessage());
            }
        }
        return renderEnterOTPPage;
    }

    @GetMapping("/resetPassword")
    public String showResetPasswordPage(RedirectAttributes redirectAttributes,
                                        Model model) {

        String emailResetPassword = (String) session.getAttribute("emailResetPassword");

        //session timeout
        if (emailResetPassword == null || emailResetPassword.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Phiên làm lại mật khẩu hết hạn.");
            return "redirect:/forgetPassword";
        }
        UserResetPasswordDto userResetPasswordDto = new UserResetPasswordDto();
        if (!model.containsAttribute("userResetPasswordDto")) {
            userResetPasswordDto.setEmail(emailResetPassword);
        } else {
            userResetPasswordDto = (UserResetPasswordDto) model.getAttribute("userResetPasswordDto");
            String otpEmail = userResetPasswordDto.getEmail();
            if (otpEmail == null || otpEmail.isEmpty()) {
                userResetPasswordDto.setEmail(emailResetPassword);
            }
        }
        model.addAttribute("userResetPasswordDto", userResetPasswordDto);
        return renderResetPasswordPage;
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@ModelAttribute("userResetPasswordDto") @Valid UserResetPasswordDto userResetPasswordDto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return renderResetPasswordPage;
        }

        try {
            userService.resetPassword(userResetPasswordDto.getEmail(), userResetPasswordDto.getPassword(), userResetPasswordDto.getConfirmPassword());
            return "dangnhap&dangky/resetThanhcong";
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("Mật khẩu")) {
                bindingResult.rejectValue("passwordConfirm", "wrongConfirmPass", "Mật khẩu xác nhận không đúng. Vui lòng thử lại");
            } else {
                bindingResult.reject("globalError", e.getMessage());
            }
        }
        return renderResetPasswordPage;
    }
}
