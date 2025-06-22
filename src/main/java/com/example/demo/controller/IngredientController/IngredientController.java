package com.example.demo.controller.IngredientController;

import com.example.demo.model.Ingredient;
import com.example.demo.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/manageinventory")  // Đường dẫn chính cho quản lý nguyên liệu
public class IngredientController {

    @Autowired
    private IngredientRepository ingredientRepo;

    // Lấy tất cả nguyên liệu và hiển thị trong bảng
    @GetMapping
    public String showAllIngredients(Model model) {
        List<Ingredient> ingredients = ingredientRepo.findAll();
        model.addAttribute("ingredients", ingredients);
        return "admin/manageInventory/listinventory";  // Đường dẫn chính xác tới file listinventory.html trong templates/admin/manageInventory
    }

    // Hiển thị form thêm nguyên liệu
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        return "admin/manageInventory/addinventory";  // Đường dẫn chính xác tới file addinventory.html trong templates/admin/manageInventory
    }

    // Xử lý khi thêm nguyên liệu
    @PostMapping("/add")
    public String addIngredient(@ModelAttribute Ingredient ingredient) {
        // Kiểm tra và thiết lập các giá trị mặc định nếu chưa nhập
        if (ingredient.getQuantityInStock() <= 0) {
            ingredient.setQuantityInStock(0);
        }

        if (ingredient.getMinimumRequired() == 0) {
            String unit = ingredient.getUnit().toLowerCase();
            switch (unit) {
                case "kg":
                    ingredient.setMinimumRequired(2);
                    break;
                case "ml":
                    ingredient.setMinimumRequired(500);
                    break;
                case "cái":
                case "hộp":
                case "lon":
                case "gói":
                    ingredient.setMinimumRequired(20);
                    break;
                case "thùng":
                    ingredient.setMinimumRequired(1);
                    break;
                default:
                    ingredient.setMinimumRequired(5.0);  // Giá trị mặc định cho các đơn vị khác
                    break;
            }
        }

        ingredient.setLastUpdated(new Date());
        ingredientRepo.save(ingredient);
        return "redirect:/admin/manageinventory";  // Đường dẫn sau khi thêm nguyên liệu
    }

    // Hiển thị form chỉnh sửa nguyên liệu
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Optional<Ingredient> optional = ingredientRepo.findById(id);
        if (optional.isPresent()) {
            model.addAttribute("ingredient", optional.get());
            return "admin/manageInventory/editinventory";  // Đường dẫn chính xác tới file editinventory.html trong templates/admin/manageInventory
        }
        return "redirect:/admin/manageinventory";  // Quay lại trang danh sách nguyên liệu nếu không tìm thấy
    }

    // Xử lý khi chỉnh sửa nguyên liệu
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
        return "redirect:/admin/manageinventory";  // Đường dẫn quay lại trang danh sách sau khi cập nhật
    }

    // Hiển thị nguyên liệu có số lượng tồn thấp (gần hết)
    @GetMapping("/low-stock")
    public String showLowStockIngredients(Model model) {
        model.addAttribute("ingredients", ingredientRepo.findByQuantityInStockLessThanEqual(5.0));
        return "admin/manageInventory/lowstock";  // Đường dẫn chính xác tới file lowstock.html trong templates/admin/manageInventory
    }
}
