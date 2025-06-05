package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHomePage() {
        return "trangchu";
    }

    @GetMapping("/feature")
    public String getFeaturePage() {
        return "navPages/features";
    }

    @GetMapping("/pricing")
    public String getPricingPage() {
        return "navPages/pricing";
    }

    @GetMapping("/about")
    public String getAboutUsPage() {
        return "navPages/aboutus";
    }
}
