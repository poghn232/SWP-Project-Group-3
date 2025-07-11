package com.example.demo.controller.CheckoutController;

import com.example.demo.api.dto.party.TableDto;
import com.example.demo.model.TableOrderDetails;
import com.example.demo.model.User;
import com.example.demo.service.CalendarService;
import com.example.demo.service.OrderService;
import com.example.demo.service.TableOrderDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Controller
public class TableController {

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TableOrderDetailsService tableOrderDetailsService;

    private final String renderChooseTablePage = "order/choose-table";

    @GetMapping("/getDefaultTables")
    public String showTablePageByDefault(Model model, @AuthenticationPrincipal User user) {

        //catch user tries to enter page without choosing party
        if (orderService.findDraftOrderByUsername(user.getUsername()) == null) {
            return "redirect:/";
        }

        //Display calendar
        model.addAttribute("calendar", calendarService.getMonthlyCalendar());
        model.addAttribute("months", calendarService.getMonths()); // display months

        //delete old order details
        tableOrderDetailsService.deleteOrdersDetailBefore(LocalDate.now());

        //default day to display
        List<TableOrderDetails> chosenDay = tableOrderDetailsService.getDefaultDay();
        chosenDay.sort(Comparator.comparingInt(TableOrderDetails::getTableNumber));

        if (!model.containsAttribute("chosenDay")) {
            model.addAttribute("chosenDay", chosenDay);
        }

        //create empty orders, first time (can delete)
        tableOrderDetailsService.manageOrders();

        return renderChooseTablePage;
    }

    @GetMapping("/getTables")
    public String showTablePage(Model model,
                                @RequestParam(name = "day") String dayChosen,
                                @RequestParam(name = "month") String monthChosen) {

        //insert order of the user's chosen date to model and display
        LocalDate chosenDate = calendarService.getCustomizedDate(dayChosen, monthChosen);
        List<TableOrderDetails> chosenDay = tableOrderDetailsService.findAllOrderDetailsByDate(chosenDate);
        model.addAttribute("chosenDay", chosenDay);
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
