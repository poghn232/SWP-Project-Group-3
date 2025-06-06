package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User currentUser = userService.getUserInfo(username);

        model.addAttribute("user", currentUser);
        return "admin/dashboard";
    }

    @GetMapping("/admin/manageProfile")
    public String showManagePage(Model model) {
        if (!model.containsAttribute("user")) {

        }
        return "admin/manageUserProfiles";
    }
}