package com.example.demo.controller.CheckoutController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentController {
    @GetMapping("/checkout")
    public String showCheckoutPage() {
        return "checkout";
    }
}
