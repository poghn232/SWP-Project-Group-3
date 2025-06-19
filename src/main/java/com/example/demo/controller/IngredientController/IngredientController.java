package com.example.demo.controller.IngredientController;

import com.example.demo.model.Ingredient;
import com.example.demo.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    @Autowired
    private IngredientRepository ingredientRepo;

    @GetMapping
    public List<Ingredient> getAllIngredients() {
        return ingredientRepo.findAll();
    }

    @PostMapping
    public String addIngredient(@RequestBody Ingredient ingredient) {
        ingredient.setLastUpdated(new Date());
        ingredientRepo.save(ingredient);
        return "Thêm nguyên liệu thành công";
    }

    @PutMapping("/{id}")
    public String updateIngredient(@PathVariable int id, @RequestBody Ingredient updated) {
        Optional<Ingredient> optional = ingredientRepo.findById(id);
        if (optional.isPresent()) {
            Ingredient ing = optional.get();
            ing.setName(updated.getName());
            ing.setUnit(updated.getUnit());
            ing.setQuantityInStock(updated.getQuantityInStock());
            ing.setMinimumRequired(updated.getMinimumRequired());
            ing.setLastUpdated(new Date());
            ingredientRepo.save(ing);
            return "Cập nhật thành công";
        }
        return "Không tìm thấy nguyên liệu";
    }

    @DeleteMapping("/{id}")
    public String deleteIngredient(@PathVariable int id) {
        if (ingredientRepo.existsById(id)) {
            ingredientRepo.deleteById(id);
            return "Đã xoá nguyên liệu";
        }
        return "Không tìm thấy nguyên liệu";
    }

    // Lấy nguyên liệu gần hết
    @GetMapping("/low-stock")
public List<Ingredient> getLowStockIngredients(@RequestParam double min) {
    return ingredientRepo.findByQuantityInStockLessThanEqual(min);
}

}
