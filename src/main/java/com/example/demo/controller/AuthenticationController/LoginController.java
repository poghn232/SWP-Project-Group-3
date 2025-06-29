package com.example.demo.controller.AuthenticationController;

import com.example.demo.api.dto.authentication.UserLoginDto;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final String renderLoginPage = "dangnhap&dangky/dangnhap";

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage(Model model,
                                @ModelAttribute("loginError") String loginError,
                                @ModelAttribute("oldUsername") String username){

        UserLoginDto userLoginDto = new UserLoginDto();
        if (username != null && !username.isEmpty()) {
            userLoginDto.setUsername(username);
        }
        model.addAttribute("userDto", userLoginDto);
        if (loginError != null && !loginError.isEmpty()) {
             model.addAttribute("errorMessage", loginError);
        }
        return renderLoginPage;
    }
}
