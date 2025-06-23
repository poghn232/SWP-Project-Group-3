package com.example.demo.controller.ReviewController;

import com.example.demo.model.Review;
import com.example.demo.model.User;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UserReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Hiển thị chi tiết sản phẩm cùng review được duyệt
     */
    @GetMapping("/user/product/{productId}")
    public String viewProductDetail(@PathVariable int productId,
                                     Model model,
                                     HttpSession session) {
        // Lấy review được duyệt
        List<Review> reviews = reviewRepository.findByItemIdAndStatus(productId, "approved");
        model.addAttribute("reviews", reviews);
        model.addAttribute("productId", productId);

        // Kiểm tra đăng nhập
        User user = (User) session.getAttribute("user"); // hoặc lấy bằng Authentication
        boolean canReview = false;

        if (user != null) {
            canReview = hasPurchasedProduct(user.getUserId(), productId);
        }
        model.addAttribute("canReview", canReview);

        return "user/product-detail"; // templates/user/product-detail.html
    }

    /**
     * Xử lý đăng review
     */
    @PostMapping("/user/product/{productId}/addReview")
    public String addReview(@PathVariable int productId,
                             @RequestParam int rating,
                             @RequestParam String comment,
                             HttpSession session) {
        User user = (User) session.getAttribute("user"); // hoặc lấy bằng Authentication
        if (user != null && hasPurchasedProduct(user.getUserId(), productId)) {
            Review review = new Review();
            review.setItemId(productId);
            review.setRating(rating);
            review.setComment(comment);
            review.setCreatedBy(user);
            review.setCreatedAt(LocalDateTime.now());
            review.setStatus("pending"); // chờ Admin duyệt
            reviewRepository.save(review);
        }
        return "redirect:/user/product/" + productId;
    }

    /**
     * Kiểm tra user có quyền review hay không
     */
    private boolean hasPurchasedProduct(int userId, int itemId) {
        // Kiểm tra trong chi tiết order:
        // 1. Xem có order nào có itemId đó không
        boolean directPurchased = orderDetailRepository.existsByUserIdAndItemId(userId, itemId);
        if (directPurchased) return true;

        // 2. Kiểm tra nếu itemId đó thuộc Combo thì user có đặt Combo đó hay không
        boolean comboPurchased = orderDetailRepository.existsByUserIdAndComboId(userId, itemId);
        return comboPurchased;
    }
}
