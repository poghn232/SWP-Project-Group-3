package com.example.demo.controller.CheckoutController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TableController {
    @GetMapping("/getTables")
    public String showTablePage() {
        Calen
        return "order/choose-table";
    }
}
