package com.example.demo.controller.CheckoutController;

import com.example.demo.model.Order;
import com.example.demo.model.Party;
import com.example.demo.model.TableOrderDetails;
import com.example.demo.model.User;
import com.example.demo.service.OrderService;
import com.example.demo.service.PartyService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
public class PaymentController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PartyService partyService;

    @GetMapping("/checkout")
    public String showCheckoutPage(Model model, @AuthenticationPrincipal User user,
                                   @RequestParam(name = "orderId", required = false) UUID orderId) {

        if (orderId == null) {
            List<Order> pendingOrders = orderService.findUnpaidPendingOrdersByUsername(user.getUsername());
            model.addAttribute("pendingOrders", pendingOrders);
        } else {
            model.addAttribute("pendingOrder", orderService.findOrderByOrderId(orderId));
        }

        return "order/checkout";
    }

    @PostMapping("/checkout")
    public String checkoutOrder(@AuthenticationPrincipal User user,
                                RedirectAttributes redirectAttributes) {
        List<Order> pendingOrders = orderService.findPendingOrdersByUsername(user.getUsername());

        if (!pendingOrders.isEmpty()) {
            orderService.successOrder(user);
            redirectAttributes.addFlashAttribute("successMessage", "Orders successfully paid! Please wait for our staffs for validating your orders on Orders head bar.");
            return "redirect:/checkout";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Your orders are out of date, please re-order.");
            return "redirect:/checkout";
        }
    }
}
