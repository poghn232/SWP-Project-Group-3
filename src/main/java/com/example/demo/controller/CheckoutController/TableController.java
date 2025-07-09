package com.example.demo.controller.CheckoutController;

import com.example.demo.api.dto.party.TableDto;
import com.example.demo.service.CalendarService;
import com.example.demo.service.TableOrderDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TableController {

    @Autowired
    private CalendarService calendar;

    @Autowired
    private TableOrderDetailsService tableOrderDetailsService;

    private final String renderChooseTablePage = "order/choose-table";

    @GetMapping("/getTables")
    public String showTablePage(Model model,
                                @RequestParam Integer partyId) {

        //Display calendar
        model.addAttribute("calendar", calendar.getMonthlyCalendar());
        model.addAttribute("months", calendar.getMonths());

        //default day to display
        if (!model.containsAttribute("chosenDay")) {
            model.addAttribute("chosenDay", tableOrderDetailsService.getDefaultDay());
        }

        //create empty orders, first time (can delete)
        tableOrderDetailsService.manageOrders();

        //if a user tries to get to this page without choosing party, bring back to home page
        if (partyId == null) {
            return "redirect:/";
        }

        return renderChooseTablePage;
    }

    @PostMapping("/getTables")
    public String getTables(@ModelAttribute("tables") @Valid TableDto tableDto,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        // if Dto has any error (usually no), render table page again
        if (bindingResult.hasErrors()) {
            return renderChooseTablePage;
        }

        return "";

    }
}
