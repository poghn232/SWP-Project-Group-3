package com.example.demo.controller.CheckoutController;

import com.example.demo.model.User;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    public String showOrdersPage(Model model, @AuthenticationPrincipal User user) {

        if (!model.containsAttribute("orders")) {
            model.addAttribute("orders", orderService.findAllOrders(user));
        }
        return "order/orders";
    }
}
