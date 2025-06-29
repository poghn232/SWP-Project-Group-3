package com.example.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHomePage() {
        return "trangchu";
    }

    @GetMapping("/error")
    public String showErrorPage() {
        return "sampleTemplate/404";
    }
}
