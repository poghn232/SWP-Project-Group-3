package com.example.demo.controller.CheckoutController;

import com.example.demo.api.dto.party.OrderDto;
import com.example.demo.model.Order;
import com.example.demo.model.TableOrderDetails;
import com.example.demo.model.User;
import com.example.demo.service.CalendarService;
import com.example.demo.service.OrderService;
import com.example.demo.service.PartyService;
import com.example.demo.service.TableOrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Controller
public class TableController {

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private PartyService partyService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TableOrderDetailsService tableOrderDetailsService;

    private final String renderChooseTablePage = "order/choose-table";

    @GetMapping("/getDefaultTables")
    public String showTablePageByDefault(Model model, @AuthenticationPrincipal User user) {

        List<TableOrderDetails> chosenDay = tableOrderDetailsService.getDefaultDay();
        chosenDay.sort(Comparator.comparingInt(TableOrderDetails::getTableNumber));
        model.addAttribute("chosenDay", chosenDay);
        //default time chosen is day
        model.addAttribute("timeChosen", "day");

        //catch user tries to enter page without choosing party
        if (orderService.findDraftOrderByUsername(user.getUsername()) == null) {
            return "redirect:/";
        }

        //Display calendar
        model.addAttribute("calendar", calendarService.getMonthlyCalendar());
        model.addAttribute("months", calendarService.getMonths()); // display months

        //delete old order details
        tableOrderDetailsService.deleteOrdersDetailBefore(LocalDate.now());

        return renderChooseTablePage;
    }

    //case: user want to change order date
    @GetMapping("/getTables")
    public String showTablePage(Model model,
                                @RequestParam(name = "day", required = false) String dayChosen,
                                @RequestParam(name = "month", required = false) String monthChosen,
                                @RequestParam(name = "time", required = false) String timeChosen,
                                RedirectAttributes redirectAttributes) {

        //case: user tries to modify url
        if (timeChosen == null || dayChosen == null || monthChosen == null) {
            return "redirect:/getDefaultTables";
        }
        if (timeChosen.isEmpty()|| dayChosen.isEmpty() || monthChosen.isEmpty()) {
            return "redirect:/getDefaultTables";
        }

        //case: user tries to change url with different permitted months
        if (!calendarService.getMonths().contains(monthChosen)) {
            redirectAttributes.addFlashAttribute("alertMessage", "The month that you searched isn't allowed");
            return "redirect:/getDefaultTables";
        }

        //case: user tries to change url with day passed permitted day of
        if (LocalDate.now().getDayOfMonth() >= Integer.parseInt(dayChosen)) {
            redirectAttributes.addFlashAttribute("alertMessage", "The day that you searched isn't allowed");
            return "redirect:/getDefaultTables";
        }
        LocalDate chosenDate = calendarService.getCustomizedDate(dayChosen, monthChosen);
        List<TableOrderDetails> chosenDay = tableOrderDetailsService.findAllOrderDetailsByDate(chosenDate);
        chosenDay.sort(Comparator.comparingInt(TableOrderDetails::getTableNumber));
        model.addAttribute("calendar", calendarService.getMonthlyCalendar());
        model.addAttribute("months", calendarService.getMonths());
        model.addAttribute("chosenDay", chosenDay);
        model.addAttribute("timeChosen", timeChosen);
        return renderChooseTablePage;
    }

    //case: user want to change slot date
    @GetMapping("/getTableSlot")
    public String showTableSlot(Model model,
                                @RequestParam(name = "time", required = false) String timeChosen,
                                @RequestParam(name = "date", required = false) LocalDate dateOfSlot) {
        if (timeChosen == null || dateOfSlot == null) {
            return "redirect:/getDefaultTables";
        }
        if (timeChosen.isEmpty()) {
            return "redirect:/getDefaultTables";
        }
        List<TableOrderDetails> chosenDay = tableOrderDetailsService.findAllOrderDetailsByDate(dateOfSlot);
        chosenDay.sort(Comparator.comparingInt(TableOrderDetails::getTableNumber));
        model.addAttribute("calendar", calendarService.getMonthlyCalendar());
        model.addAttribute("months", calendarService.getMonths());
        model.addAttribute("chosenDay", chosenDay);
        model.addAttribute("timeChosen", timeChosen);
        return renderChooseTablePage;
    }

    @PostMapping("/getTables")
    public String getTables(@RequestParam(name = "selectedTables", required = false) List<Integer> selectedTables,
                            @RequestParam(name = "selectedDate", required = false) LocalDate selectedDate,
                            @RequestParam(name = "selectedTime", required = false) String selectedTime,
                            @AuthenticationPrincipal User user,
                            RedirectAttributes redirectAttributes) {

        orderService.addTablesToOrder(selectedTables, selectedDate, selectedTime, user);

        //display order details from user that newly ordered
        Order order = orderService.findPendingOrderByUsername(user.getUsername());

        OrderDto orderDto = new OrderDto(order.getOrderId(),
                                        partyService.findById(order.getPartyId()).getPartyName(),
                                        order.getOrderDate(),
                                        order.getTotalPrice(),
                                        order.getPaymentStatus());

        redirectAttributes.addFlashAttribute("newOrder", orderDto);
        redirectAttributes.addFlashAttribute("successMessage", "Your order has been successfully registered!");
        redirectAttributes.addFlashAttribute("alertMessage", "You have 5 minutes to checkout your order.");


        return "redirect:/order-success";

    }
    @GetMapping("/order-success")
    public String showSuccessOrderPage(Model model) {

        //user reload page || user previous page
        if (!model.containsAttribute("newOrder")) {
            return "redirect:/checkout";
        }

        return "order/order-success";
    }
}
