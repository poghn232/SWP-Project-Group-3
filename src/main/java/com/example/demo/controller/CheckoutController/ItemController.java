package com.example.demo.controller.CheckoutController;

import com.example.demo.model.Party;
import com.example.demo.repository.PartyRepository;
import com.example.demo.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ItemController {

    @Autowired
    private PartyService partyService;

    private final String renderGetItemPage = "order/confirm-item";

    @GetMapping("/getItems")
    public String showConfirmItemPage(Model model,
                                      @RequestParam Integer partyId) {
        Party partyChosen = partyService.findById(partyId);
        model.addAttribute("party", partyChosen);
        model.addAttribute("totalPrice", partyService.totalPrice(partyChosen));
        return renderGetItemPage;
    }

    @PostMapping("/getItems")
    public String createOrder(@RequestParam Integer partyId){

        if (partyId == null) {
            return "redirect:/";
        }

        return "redirect:/getItems?partyId=" + partyId;
    }
}
