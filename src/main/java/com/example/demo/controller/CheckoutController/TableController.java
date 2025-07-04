package com.example.demo.controller.CheckoutController;

import com.example.demo.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TableController {

    @Autowired
    private CalendarService calendar;

    @GetMapping("/getTables")
    public String showTablePage(Model model) {
        model.addAttribute("calendar", calendar.getMonthlyCalendar());
        model.addAttribute("months", calendar.getMonths());
        return "order/choose-table";
    }
}
