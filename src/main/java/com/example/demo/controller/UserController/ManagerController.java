package com.example.demo.controller.UserController;

import com.example.demo.model.Item;
import com.example.demo.model.Party;
import com.example.demo.service.ItemService;
import com.example.demo.service.PartyService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
public class ManagerController {

    @Autowired
    private PartyService partyService;

    @Autowired
    private ItemService itemService;

    @GetMapping("/manager/dashboard")
    public String showManagerDashboardPage() {
        return "manager/manager_dashboard";
    }

    @GetMapping("/manager/manage-party")
    public String showManagerCreatePartyPage(Model model) {

        if (!model.containsAttribute("parties")) {
            model.addAttribute("parties", partyService.getAllParties());
        }

        if (!model.containsAttribute("party")) {
            model.addAttribute("party", new Party());
        }
        List<String> partyTypes = Arrays.asList("Party", "Buffet");
        model.addAttribute("partyTypes", partyTypes);


        return "manager/manage_party";
    }

    @PostMapping("/manager/manage-party")
    public String getNewPartyByManager(@ModelAttribute(name = "party") Party party,
                                       @RequestParam("partyImageFile") MultipartFile partyImageFile,
                                       RedirectAttributes redirectAttributes) throws IOException {

        partyService.addNewPartyByManager(party, partyImageFile);

        redirectAttributes.addFlashAttribute("successMessage", "Your party was created. Now you have to create items");

        return "redirect:/manager/dashboard";
    }

    @GetMapping("/manager/manage-party-food")
    public String showManagerManagePartyFoodPage(Model model) {

        List<Party> parties = partyService.getAllParties();
        if (!model.containsAttribute("parties")) {
            model.addAttribute("parties", parties);
        }

        return "manager/manage_party_food";
    }

    @PostMapping("/manager/delete-party")
    public String deletePartyByManager(@RequestParam(name = "partyId") Integer partyId,
                                       RedirectAttributes redirectAttributes){

        String partyName = partyService.deleteParty(partyId);

        redirectAttributes.addFlashAttribute("successMessage", "Party " + partyName + " was deleted successfully!");

        return "redirect:/manager/manage-party";
    }

    @GetMapping("manager/modify-party")
    public String showManagerModifyPartyPage(@RequestParam(name = "partyId") Integer partyId,
                                             Model model) {

        if (!model.containsAttribute("party")) {
            model.addAttribute("party", partyService.findById(partyId));
        }
        List<String> partyTypes = Arrays.asList("Party", "Buffet");
        if (!model.containsAttribute("partyTypes")) {
            model.addAttribute("partyTypes", partyTypes);
        }

        return "manager/modify_party";
    }

    @PostMapping("manager/modify-party")
    public String modifyPartyByManager(@RequestParam("partyImageFile") MultipartFile partyImageFile,
                                       @ModelAttribute(name = "party") Party party,
                                       @RequestParam("oldPartyName") String oldPartyName,
                                       RedirectAttributes redirectAttributes) throws IOException {
        partyService.modifyPartyByManager(party, partyImageFile);

        redirectAttributes.addFlashAttribute("successMessage", "Party: " + oldPartyName + " was successfully modified!");

        return "redirect:/manager/manage-party";
    }

    @GetMapping("/manager/manage-party-items")
    public String showPartyItemsPageByManager(@RequestParam("partyId") Integer partyId,
                                              Model model) {
        Party partyModified = partyService.findById(partyId);

        if (!model.containsAttribute("partyId")) {
            model.addAttribute("partyId", partyId);
        }

        if (!model.containsAttribute("partyName")) {
            model.addAttribute("partyName", partyModified.getPartyName());
        }

        if (!model.containsAttribute("item")) {
            model.addAttribute("item", new Item());
        }

        if (!model.containsAttribute("items")) {
            model.addAttribute("items", partyModified.getItems());
        }

        return "manager/manage_party_items";
    }

    @PostMapping("/manager/create-item")
    public String createNewItemByManager(@RequestParam("partyId") Integer partyId,
                                         @ModelAttribute("item") Item item,
                                         RedirectAttributes redirectAttributes) {

        itemService.createNewItem(partyId, item);

        redirectAttributes.addFlashAttribute("successMessage", "New item was created on party: " + partyService.findById(partyId).getPartyName());

        return "redirect:/manager/manage-party-items?partyId=" + partyId;
    }

    @GetMapping("/manager/modify-item")
    public String modifyItemByManager(@RequestParam("itemId") Integer itemId,
                                      @RequestParam("partyId") Integer partyId,
                                      Model model) {

        Item item = itemService.findById(itemId);

        if(!model.containsAttribute("item")) {
            model.addAttribute("item", item);
        }

        if (!model.containsAttribute("partyId")) {
            model.addAttribute("partyId", partyId);
        }

        return "manager/modify_item";
    }

    @PostMapping("/manager/modify-item")
    public String modifyItemByManager(@ModelAttribute("item") Item item,
                                      @RequestParam("partyId") Integer partyId,
                                      RedirectAttributes redirectAttributes) {
        itemService.modifyItem(item, partyId);

        redirectAttributes.addFlashAttribute("successMessage", "Item : " + item.getItemName() + " was modified!");
        return "redirect:/manager/manage-party-items?partyId=" + partyId;
    }

    @PostMapping("/manager/delete-item")
    public String deleteItemByManager(@RequestParam("partyId") Integer partyId,
                                      @RequestParam("itemId") Integer itemId,
                                      RedirectAttributes redirectAttributes) {
        String itemName = itemService.findById(itemId).getItemName();

        itemService.deleteItem(partyId, itemId);

        redirectAttributes.addFlashAttribute("partyId", partyId);
        redirectAttributes.addFlashAttribute("successMessage", "Item :" + itemName + " was deleted successfully!");

        return "redirect:/manager/manage-party-items?partyId=" + partyId;
    }
}
