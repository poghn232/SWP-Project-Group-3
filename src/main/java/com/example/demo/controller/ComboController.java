package com.example.demo.controller;

import com.example.demo.model.Combo;
import com.example.demo.model.ComboDetail;
import com.example.demo.repository.ComboRepository;
import com.example.demo.repository.ComboDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/combos")
public class ComboController {

    @Autowired
    private ComboRepository comboRepository;

    @Autowired
    private ComboDetailRepository comboDetailRepository;

    // Combo CRUD

    @GetMapping
    public List<Combo> getAllCombos() {
        return comboRepository.findAll();
    }

    @GetMapping("/{id}")
    public Combo getComboById(@PathVariable int id) {
        return comboRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Combo createCombo(@RequestBody Combo combo) {
        return comboRepository.save(combo);
    }

    @PutMapping("/{id}")
    public Combo updateCombo(@PathVariable int id, @RequestBody Combo combo) {
        if (!comboRepository.existsById(id)) {
            return null;
        }
        combo.setComboId(id);
        return comboRepository.save(combo);
    }

    @DeleteMapping("/{id}")
    public void deleteCombo(@PathVariable int id) {
        comboRepository.deleteById(id);
    }

    // ComboDetail CRUD

    @GetMapping("/{comboId}/details")
    public List<ComboDetail> getComboDetails(@PathVariable int comboId) {
        return comboDetailRepository.findByComboId(comboId);
    }

    @PostMapping("/{comboId}/details")
    public ComboDetail createComboDetail(@PathVariable int comboId, @RequestBody ComboDetail comboDetail) {
        comboDetail.setComboId(comboId);
        return comboDetailRepository.save(comboDetail);
    }

    @PutMapping("/details/{detailId}")
    public ComboDetail updateComboDetail(@PathVariable int detailId, @RequestBody ComboDetail comboDetail) {
        if (!comboDetailRepository.existsById(detailId)) {
            return null;
        }
        comboDetail.setComboDetailId(detailId);
        return comboDetailRepository.save(comboDetail);
    }

    @DeleteMapping("/details/{detailId}")
    public void deleteComboDetail(@PathVariable int detailId) {
        comboDetailRepository.deleteById(detailId);
    }
}
