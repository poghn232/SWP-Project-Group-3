package com.example.demo.controller;

import com.example.demo.model.Combo;
import com.example.demo.model.ComboDetail;
import com.example.demo.repository.ComboRepository;
import com.example.demo.repository.ComboDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/manageCombo")
public class ComboController {

    @Autowired
    private ComboRepository comboRepository;

    @Autowired
    private ComboDetailRepository comboDetailRepository;

    // Combo CRUD

    @GetMapping
    public ResponseEntity<List<Combo>> getAllCombos() {
        return ResponseEntity.ok(comboRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Combo> getComboById(@PathVariable int id) {
        return comboRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Combo> createCombo(@RequestBody Combo combo) {
        Combo savedCombo = comboRepository.save(combo);
        return ResponseEntity.ok(savedCombo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Combo> updateCombo(@PathVariable int id, @RequestBody Combo combo) {
        if (!comboRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        combo.setComboId(id);
        Combo updatedCombo = comboRepository.save(combo);
        return ResponseEntity.ok(updatedCombo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCombo(@PathVariable int id) {
        if (!comboRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        comboRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ComboDetail CRUD

    @GetMapping("/{comboId}/details")
    public ResponseEntity<List<ComboDetail>> getComboDetails(@PathVariable int comboId) {
        return ResponseEntity.ok(comboDetailRepository.findByComboId(comboId));
    }

    @PostMapping("/{comboId}/details")
    public ResponseEntity<ComboDetail> createComboDetail(@PathVariable int comboId, @RequestBody ComboDetail comboDetail) {
        comboDetail.setComboId(comboId);
        ComboDetail savedDetail = comboDetailRepository.save(comboDetail);
        return ResponseEntity.ok(savedDetail);
    }

    @PutMapping("/details/{detailId}")
    public ResponseEntity<ComboDetail> updateComboDetail(@PathVariable int detailId, @RequestBody ComboDetail comboDetail) {
        if (!comboDetailRepository.existsById(detailId)) {
            return ResponseEntity.notFound().build();
        }
        comboDetail.setComboDetailId(detailId);
        ComboDetail updatedDetail = comboDetailRepository.save(comboDetail);
        return ResponseEntity.ok(updatedDetail);
    }

    @DeleteMapping("/details/{detailId}")
    public ResponseEntity<Void> deleteComboDetail(@PathVariable int detailId) {
        if (!comboDetailRepository.existsById(detailId)) {
            return ResponseEntity.notFound().build();
        }
        comboDetailRepository.deleteById(detailId);
        return ResponseEntity.noContent().build();
    }
}
