package com.example.demo.controller.UserController;

import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
public class StaffController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/staff/dashboard")
    public String showStaffDashboardPage() {
        return "staff/staff_dashboard";
    }

    @GetMapping("/staff/confirm-paid-orders")
    public String showStaffDashBoardPage(Model model) {
        if (!model.containsAttribute("orders")) {
            model.addAttribute("orders", orderService.findAllPaidPendingOrders());
        }
        if (!model.containsAttribute("allStatus")) {
            List<String> allOrderStatus = Arrays.asList("PENDING", "CONFIRMED", "CANCELLED");
            model.addAttribute("allStatus", allOrderStatus);
        }
        return "staff/confirm_order";
    }
    @PostMapping("/staff/confirm-paid-orders")
    public String changeStatus(@RequestParam(name = "orderId") UUID orderId,
                               @RequestParam(name = "status") String status,
                               RedirectAttributes redirectAttributes) {

        orderService.updateOrderStatusByStaff(orderId, status);
        redirectAttributes.addFlashAttribute("successMessage", "Order: " + orderId + " successfully updated!" );
        return "redirect:/staff/dashboard";
    }
    @GetMapping("/staff/check-in")
    public String showCheckinPage(Model model) {
        if (!model.containsAttribute("orders")) {
            model.addAttribute("orders", orderService.findAllPaidConfirmedOrders());
        }
        if (!model.containsAttribute("allStatus")) {
            List<String> allOrderStatus = Arrays.asList("CONFIRMED", "CANCELLED", "COMPLETED");
            model.addAttribute("allStatus", allOrderStatus);
        }
        return "staff/check_in";
    }
    @PostMapping("/staff/check-in")
    public String changeCheckinStatus(@RequestParam(name = "orderId") UUID orderId,
                               @RequestParam(name = "status") String status,
                               RedirectAttributes redirectAttributes) {

        orderService.updateOrderStatusByStaff(orderId, status);
        redirectAttributes.addFlashAttribute("successMessage", "Order: " + orderId + " completed!" );
        return "redirect:/staff/dashboard";
    }
}
