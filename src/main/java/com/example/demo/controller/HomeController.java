package com.example.demo.controller;
import com.example.demo.api.dto.party.PartyDto;
import com.example.demo.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private PartyService partyService;

    @GetMapping("/")
    public String getHomePage(Model model) {
        if (!model.containsAttribute("partyDto")) {
            PartyDto partyDto = new PartyDto(partyService.getAllParties());
            model.addAttribute("partyDto", partyDto);
        }
        return "trangchu";
    }

    @GetMapping("/error")
    public String showErrorPage() {
        return "sampleTemplate/404";
    }
}
