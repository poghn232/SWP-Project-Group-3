package com.example.demo.repository;

import com.example.demo.model.ComboDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ComboDetailRepository extends JpaRepository<ComboDetail, Integer> {

    List<ComboDetail> findByComboId(int comboId);

    @Transactional
    void deleteByComboId(int comboId);
}
