package com.example.demo.repository;

import com.example.demo.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    List<Ingredient> findByQuantityInStockLessThanEqual(double minimum);
}
