package com.example.demo.controller.UserController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {
    @GetMapping("/customer/dashboard")
    public String getDashBoard() {
        return "customer/dashboard";
    }
}
