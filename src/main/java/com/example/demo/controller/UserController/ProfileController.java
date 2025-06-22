package com.example.demo.controller.UserController;

import com.example.demo.api.dto.UserDto;
import com.example.demo.api.dto.authentication.UserRegisterDto;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String getProfile(@ModelAttribute("authenticatedUserDto") @Valid UserDto authenticatedUserDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return renderProfilePage;
        }
    }

    @GetMapping("/change-password")
    public String showResetPasswordPage() {
        return "change-password";
    }

    @PostMapping("/change-password")
    public String getNewPassword() {
        return "";
    }
}
