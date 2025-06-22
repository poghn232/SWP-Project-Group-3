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
@RequestMapping("/admin/manageinventory")  // Đường dẫn chính cho quản lý nguyên liệu
public class IngredientPageController {

    @Autowired
    private IngredientRepository ingredientRepo;

    // Lấy tất cả nguyên liệu và hiển thị trong bảng
    @GetMapping
    public String showAllIngredients(Model model) {
        model.addAttribute("ingredients", ingredientRepo.findAll());
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
            ingredient.setQuantityInStock(0);  // Set quantity tồn kho mặc định là 0
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

        ingredient.setLastUpdated(new Date());  // Cập nhật thời gian
        ingredientRepo.save(ingredient);
        return "redirect:/admin/manageinventory";  // Sau khi thêm, chuyển hướng về trang danh sách nguyên liệu
    }

    // Hiển thị form chỉnh sửa nguyên liệu
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Optional<Ingredient> optional = ingredientRepo.findById(id);
        if (optional.isPresent()) {
            model.addAttribute("ingredient", optional.get());
            return "admin/manageInventory/editinventory";  // Đường dẫn chính xác tới file editinventory.html trong templates/admin/manageInventory
        }
        return "redirect:/admin/manageinventory";  // Nếu không tìm thấy nguyên liệu, quay lại trang danh sách
    }

    // Xử lý khi cập nhật nguyên liệu
    @PostMapping("/edit/{id}")
    public String updateIngredient(@PathVariable int id, @ModelAttribute Ingredient updated) {
        Optional<Ingredient> optional = ingredientRepo.findById(id);
        if (optional.isPresent()) {
            Ingredient ing = optional.get();
            ing.setName(updated.getName());
            ing.setUnit(updated.getUnit());
            ing.setQuantityInStock(updated.getQuantityInStock());
            ing.setMinimumRequired(updated.getMinimumRequired());
            ing.setLastUpdated(new Date());  // Cập nhật thời gian
            ingredientRepo.save(ing);
        }
        return "redirect:/admin/manageinventory";  // Sau khi cập nhật, quay lại danh sách nguyên liệu
    }

    // Xóa nguyên liệu
    @GetMapping("/delete/{id}")
    public String deleteIngredient(@PathVariable int id) {
        ingredientRepo.deleteById(id);  // Xoá nguyên liệu theo id
        return "redirect:/admin/manageinventory";  // Sau khi xóa, quay lại trang danh sách nguyên liệu
    }

    // Hiển thị nguyên liệu có số lượng tồn thấp (gần hết)
    @GetMapping("/low-stock")
    public String showLowStockIngredients(Model model) {
        model.addAttribute("ingredients", ingredientRepo.findByQuantityInStockLessThanEqual(5.0));
        return "admin/manageInventory/lowstock";  // Đường dẫn chính xác tới file lowstock.html trong templates/admin/manageInventory
    }
}
