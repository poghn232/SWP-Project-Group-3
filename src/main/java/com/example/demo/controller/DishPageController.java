package com.example.demo.controller;

import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/manageDish") // Thêm prefix cho dễ tổ chức
public class DishPageController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/list")
    public String viewItems(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "admin/manageDish/listdish"; // đường dẫn đúng với file JSP
    }

    @GetMapping("/add")
    public String addForm() {
        return "admin/manageDish/adddish";
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
        return "redirect:/admin/manageDish/list";
    }

    @GetMapping("/edit")
    public String editForm(@RequestParam int id, Model model) {
        model.addAttribute("item", itemRepository.findById(id).orElse(null));
        return "admin/manageDish/editdish";
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
        return "redirect:/admin/manageDish/list";
    }

    @GetMapping("/delete")
    public String deleteItem(@RequestParam int id) {
        itemRepository.deleteById(id);
        return "redirect:/admin/manageDish/list";
    }
}

