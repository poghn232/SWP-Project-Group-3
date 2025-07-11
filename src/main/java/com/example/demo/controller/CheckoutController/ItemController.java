package com.example.demo.controller.CheckoutController;

import com.example.demo.model.Item;
import com.example.demo.model.Order;
import com.example.demo.model.Party;
import com.example.demo.model.User;
import com.example.demo.repository.PartyRepository;
import com.example.demo.service.OrderService;
import com.example.demo.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ItemController {

    @Autowired
    private PartyService partyService;

    @Autowired
    private OrderService orderService;

    private final String renderGetItemPage = "order/confirm-item";

    @GetMapping("/getItems")
    public String showConfirmItemPage(Model model,
                                      @RequestParam Integer partyId,
                                      @AuthenticationPrincipal User user) {
        // If user somehow discontinued their order and then start again at Order now button on the home page,
        // redirect them to the table order page.
        // This wont make user have multiples draft orders.
        if (orderService.findDraftOrderByUsername(user.getUsername()) != null){
            return "redirect:/getDefaultTables";
        }

        //<-- user tries to change url or enter url without parameter
        if (partyId == null) {
            return "redirect:/";
        }

        Party partyChosen = partyService.findById(partyId);

        if (partyChosen == null) {
            return "redirect:/";
        }
        // -->

        model.addAttribute("party", partyChosen);
        model.addAttribute("totalPrice", partyService.totalPrice(partyChosen));
        model.addAttribute("partyId", partyId);
        return renderGetItemPage;
    }

    @PostMapping("/getItems")
    public String createOrder(@RequestParam Integer partyId,
                              @RequestParam(name = "orderNote", required = false) String notes,
                              RedirectAttributes redirectAttributes,
                              @AuthenticationPrincipal User user) {

        // again, make sure they are not processing with unidentified party id
        if (partyId == null) {
            return "redirect:/";
        }

        //create new order to db, with draft status
        orderService.createNewOrder(partyId, notes, user.getUsername(), user.getPhone(), user);

        return "redirect:/getDefaultTables";
    }
}
