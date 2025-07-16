package com.example.demo.controller.CheckoutController;

import com.example.demo.model.Order;
import com.example.demo.model.Party;
import com.example.demo.model.User;
import com.example.demo.service.OrderService;
import com.example.demo.service.PartyService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PaymentController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PartyService partyService;

    @GetMapping("/checkout")
    public String showCheckoutPage(Model model, @AuthenticationPrincipal User user) {


        if (user != null) {
            List<Order> orders = orderService.findPendingOrdersByUsername(user.getUsername());
            model.addAttribute("pendingOrder", orders);
        }

        return "order/checkout";
    }
}
