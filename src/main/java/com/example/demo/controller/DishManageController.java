package com.example.demo.controller;

import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dishes")
public class DishManageController {

    @Autowired
    private ItemRepository itemRepository;

    // Lấy tất cả món ăn
    @GetMapping
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // Thêm món ăn
    @PostMapping
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        return ResponseEntity.ok(itemRepository.save(item));
    }

    // Sửa món ăn theo ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@PathVariable int id, @RequestBody Item updatedItem) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setName(updatedItem.getName());
            item.setDescription(updatedItem.getDescription());
            item.setPrice(updatedItem.getPrice());
            item.setCategory(updatedItem.getCategory());
            item.setImageUrl(updatedItem.getImageUrl());
            item.setAvailable(updatedItem.isAvailable());
            return ResponseEntity.ok(itemRepository.save(item));
        }
        return ResponseEntity.notFound().build();
    }

    // Xóa món ăn
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable int id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
