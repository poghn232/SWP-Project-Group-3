package com.example.demo.api.foodmanage;

import com.example.demo.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class DishPageController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/list")
    public String viewItems(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "views/list"; // Tương ứng WEB-INF/views/list.jsp
    }

    @GetMapping("/add")
    public String addForm() {
        return "views/add";
    }

    @PostMapping("/add")
    public String addItem(HttpServletRequest req) {
        Item item = new Item();
        item.setName(req.getParameter("name"));
        item.setDescription(req.getParameter("description"));
        item.setPrice(Double.parseDouble(req.getParameter("price")));
        item.setCategory(req.getParameter("category"));
        item.setImageUrl(req.getParameter("imageUrl"));
        item.setAvailable(req.getParameter("isAvailable") != null);
        itemRepository.save(item);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editForm(@RequestParam int id, Model model) {
        model.addAttribute("item", itemRepository.findById(id).orElse(null));
        return "views/edit";
    }

    @PostMapping("/edit")
    public String editItem(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("itemId"));
        Item item = itemRepository.findById(id).orElse(null);
        if (item != null) {
            item.setName(req.getParameter("name"));
            item.setDescription(req.getParameter("description"));
            item.setPrice(Double.parseDouble(req.getParameter("price")));
            item.setCategory(req.getParameter("category"));
            item.setImageUrl(req.getParameter("imageUrl"));
            item.setAvailable(req.getParameter("isAvailable") != null);
            itemRepository.save(item);
        }
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteItem(@RequestParam int id) {
        itemRepository.deleteById(id);
        return "redirect:/";
    }
}
