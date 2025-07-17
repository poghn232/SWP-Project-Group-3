package com.example.demo.controller.UserController;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(Model model) {
        if (!model.containsAttribute("users")) {
            model.addAttribute("users", userService.findAllUsersWithoutAdmin());
        }
        return "admin/admin_dashboard";
    }

    @GetMapping("/admin/manageProfile")
    public String showProfileToModify(@RequestParam(name = "customerName") String username,
                                      Model model) {

        List<String> allRoles = Arrays.asList("customer", "staff", "admin", "manager");
        User modifyUser = userService.findUserByUsername(username);
        if (!model.containsAttribute("user")) {
            model.addAttribute("allRoles", allRoles);
            model.addAttribute("user", modifyUser);
        }

        return "admin/modify_user";
    }

    @PostMapping("/admin/manageProfile")
    public String modifyUser(@Valid @ModelAttribute("user") User updatedUser,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // Quay lại form nếu có lỗi validate
            return "admin/modify_user";
        }

        userService.modifyUserByAdmin(updatedUser);

        redirectAttributes.addFlashAttribute("successMessage", "User profile updated successfully!");
        return "redirect:/admin/dashboard";
    }
}