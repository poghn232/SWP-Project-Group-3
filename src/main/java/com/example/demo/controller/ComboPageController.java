package com.example.demo.controller;

import com.example.demo.model.Combo;
import com.example.demo.model.ComboDetail;
import com.example.demo.model.Item;
import com.example.demo.repository.ComboDetailRepository;
import com.example.demo.repository.ComboRepository;
import com.example.demo.repository.ItemRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/manageCombo")
public class ComboPageController {

    @Autowired
    private ComboRepository comboRepository;

    @Autowired
    private ComboDetailRepository comboDetailRepository;

    @Autowired
    private ItemRepository itemRepository;

    // Hiển thị danh sách combo
    @GetMapping("/list")
    public String listCombos(Model model) {
        List<Combo> combos = comboRepository.findAll();
        model.addAttribute("combos", combos);
        return "admin/manageCombo/listcombo";  // Đảm bảo đường dẫn view đúng
    }

    // Hiển thị chi tiết combo
    @GetMapping("/detail/{id}")
    public String comboDetail(@PathVariable int id, Model model) {
        Combo combo = comboRepository.findById(id).orElse(null);
        if (combo == null) {
            return "error/404";  // Nếu không tìm thấy combo, trả về trang lỗi
        }
        List<ComboDetail> comboDetails = comboDetailRepository.findByComboId(id);

        List<Map<String, Object>> detailWithItemName = comboDetails.stream().map(detail -> {
            Map<String, Object> map = new HashMap<>();
            map.put("comboDetailId", detail.getComboDetailId());
            map.put("itemId", detail.getItemId());
            map.put("quantity", detail.getQuantity());

            Item item = itemRepository.findById(detail.getItemId()).orElse(null);
            map.put("itemName", item != null ? item.getName() : "Không tìm thấy");

            return map;
        }).collect(Collectors.toList());  // Sửa thành collect() thay vì toList()

        model.addAttribute("combo", combo);
        model.addAttribute("comboDetails", detailWithItemName);
        return "admin/manageCombo/combodetail";  // Đảm bảo đường dẫn view đúng
    }

    // Hiển thị trang tạo combo
    @GetMapping("/create")
    public String showCreateComboPage(Model model) {
        List<Item> items = itemRepository.findAll();
        Set<String> categories = items.stream()
                                      .map(Item::getCategory)
                                      .collect(Collectors.toSet());

        model.addAttribute("items", items);
        model.addAttribute("categories", categories);
        model.addAttribute("combo", new Combo());
        return "admin/manageCombo/addcombo";  // Đảm bảo đường dẫn view đúng
    }

    // Xử lý lưu combo mới
    @PostMapping("/create")
    public String createCombo(@ModelAttribute Combo combo,
                               @RequestParam("itemIds") List<Integer> itemIds,
                               @RequestParam("quantities") List<Integer> quantities) {

        comboRepository.save(combo);

        for (int i = 0; i < itemIds.size(); i++) {
            ComboDetail detail = new ComboDetail();
            detail.setComboId(combo.getComboId());
            detail.setItemId(itemIds.get(i));
            detail.setQuantity(quantities.get(i));
            comboDetailRepository.save(detail);
        }

        return "redirect:/admin/manageCombo/list";  // Đảm bảo URL chính xác
    }

    // Hiển thị trang sửa combo
    @GetMapping("/edit/{id}")
    public String showEditComboPage(@PathVariable int id, Model model) {
        Combo combo = comboRepository.findById(id).orElse(null);
        if (combo == null) {
            return "error/404";  // Nếu không tìm thấy combo, trả về trang lỗi
        }
        List<Item> items = itemRepository.findAll();
        Set<String> categories = items.stream()
                                      .map(Item::getCategory)
                                      .collect(Collectors.toSet());

        model.addAttribute("combo", combo);
        model.addAttribute("items", items);
        model.addAttribute("categories", categories);
        return "admin/manageCombo/editcombo";  // Đảm bảo đường dẫn view đúng
    }

    // Xử lý sửa combo
    @PostMapping("/edit/{id}")
    public String editCombo(@PathVariable int id,
                             @ModelAttribute Combo combo,
                             @RequestParam("itemIds") List<Integer> itemIds,
                             @RequestParam("quantities") List<Integer> quantities) {

        combo.setComboId(id);
        comboRepository.save(combo);

        comboDetailRepository.deleteByComboId(id);

        for (int i = 0; i < itemIds.size(); i++) {
            ComboDetail detail = new ComboDetail();
            detail.setComboId(combo.getComboId());
            detail.setItemId(itemIds.get(i));
            detail.setQuantity(quantities.get(i));
            comboDetailRepository.save(detail);
        }

        return "redirect:/admin/manageCombo/list";  // Đảm bảo URL chính xác
    }

    // Xử lý xóa combo
    @GetMapping("/delete/{id}")
    public String deleteCombo(@PathVariable int id) {
        comboDetailRepository.deleteByComboId(id);
        comboRepository.deleteById(id);
        return "redirect:/admin/manageCombo/list";  // Đảm bảo URL chính xác
    }
}
