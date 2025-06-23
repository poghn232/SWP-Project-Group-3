package com.example.demo.controller.UserController;

import com.example.demo.api.dto.account.UserDto;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    private final String renderProfilePage = "profile";

    @GetMapping("/profile")
    public String showProfile(Model model, @AuthenticationPrincipal User user) {
        UserDto authenticatedUserDto = new UserDto(user.getUsername(), user.getEmail(), user.getPhone());
        if (!model.containsAttribute("authenticatedUserDto")) {
            model.addAttribute("authenticatedUserDto", authenticatedUserDto);
        }
        return renderProfilePage;
    }

    @PostMapping("/profile")
    public String getProfile(@ModelAttribute("authenticatedUserDto") @Valid UserDto manageAccountUserDto,
                             BindingResult bindingResult,
                             @AuthenticationPrincipal User user,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return renderProfilePage;
        }

        try {

            User updatedUser = userService.changeUserInformation(user.getUsername(), manageAccountUserDto);

            Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication(); // Lấy auth hiện tại để giữ credentials
            UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                    updatedUser, // Principal mới: đối tượng User đã được cập nhật (implement UserDetails)
                    currentAuth.getCredentials(), // Giữ nguyên credentials (mật khẩu đã băm)
                    updatedUser.getAuthorities() // Lấy lại quyền hạn từ đối tượng User đã cập nhật
            );

            SecurityContextHolder.getContext().setAuthentication(newAuth);

            //display new changes to manage page
            UserDto authenticatedUserDto = new UserDto(updatedUser.getUsername(), updatedUser.getEmail(), updatedUser.getPhone());
            model.addAttribute("authenticatedUserDto", authenticatedUserDto);
            redirectAttributes.addFlashAttribute("successMessage","Your information has been updated successfully!");
            return "redirect:/profile";
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("Email đã tồn tại")){
                bindingResult.rejectValue("email", "existedEmail", "Email existed. Try again");
            } else if (e.getMessage().contains("Tên tài khoản đã tồn tại")) {
                bindingResult.rejectValue("username", "existedUsername", "Username existed. Try again");
            } else if (e.getMessage().contains("Số điện thoại đã tồn tại")) {
                bindingResult.rejectValue("phone", "existedPhoneNumber", "Phone number existed. Try again");
            } else {
                bindingResult.reject("unexpectedError", "Unexpected error!");
            }
            return renderProfilePage;
        }
    }

    @GetMapping("/change-password")
    public String showResetPasswordPage() {
        return "change-password";
    }

    @PostMapping("/change-password") //TODO
    public String getNewPassword() {
        return "";
    }

    @PostMapping("/delete-account") //TODO
    public String deleteAccount() {
        return "";
    }
}
