package com.example.demo.controller.UserController;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        return "admin/dashboard";
    }

    @GetMapping("/admin/manageProfile")
    public String showManagePage(Model model) {
        return "admin/manageUserProfiles";
    }

    @GetMapping("/admin/addUser")
    public String addNewUser(Model model) {
        return "";
    }
}