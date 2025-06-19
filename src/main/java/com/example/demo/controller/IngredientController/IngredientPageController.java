package com.example.demo.controller.IngredientController;

import com.example.demo.model.Ingredient;
import com.example.demo.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/ingredients")
public class IngredientPageController {

    @Autowired
    private IngredientRepository ingredientRepo;

    @GetMapping
    public String showAllIngredients(Model model) {
        model.addAttribute("ingredients", ingredientRepo.findAll());
        return "ingredient/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        return "ingredient/add";
    }

    @PostMapping("/add")
    public String addIngredient(@ModelAttribute Ingredient ingredient) {
        ingredient.setLastUpdated(new Date());
        ingredientRepo.save(ingredient);
        return "redirect:/ingredients";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Optional<Ingredient> optional = ingredientRepo.findById(id);
        if (optional.isPresent()) {
            model.addAttribute("ingredient", optional.get());
            return "ingredient/edit";
        }
        return "redirect:/ingredients";
    }

    @PostMapping("/edit/{id}")
    public String updateIngredient(@PathVariable int id, @ModelAttribute Ingredient updated) {
        Optional<Ingredient> optional = ingredientRepo.findById(id);
        if (optional.isPresent()) {
            Ingredient ing = optional.get();
            ing.setName(updated.getName());
            ing.setUnit(updated.getUnit());
            ing.setQuantityInStock(updated.getQuantityInStock());
            ing.setMinimumRequired(updated.getMinimumRequired());
            ing.setLastUpdated(new Date());
            ingredientRepo.save(ing);
        }
        return "redirect:/ingredients";
    }

    @GetMapping("/delete/{id}")
    public String deleteIngredient(@PathVariable int id) {
        ingredientRepo.deleteById(id);
        return "redirect:/ingredients";
    }

    @GetMapping("/low-stock")
    public String showLowStockIngredients(Model model) {
        model.addAttribute("ingredients", ingredientRepo.findByQuantityInStockLessThanEqual(5.0));
        return "ingredient/low_stock";
    }
}
