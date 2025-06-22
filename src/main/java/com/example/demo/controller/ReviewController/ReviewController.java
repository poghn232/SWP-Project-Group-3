package com.example.demo.controller.ReviewController;

import com.example.demo.model.Review;
import com.example.demo.model.User;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    // ==========================
    // ADMIN: Quản lý đánh giá
    // ==========================
    // Xem tất cả review
    @GetMapping("/admin/reviews")
    public String listAllReviews(Model model) {
        List<Review> reviews = reviewRepository.findAll();
        model.addAttribute("reviews", reviews);
        return "admin/review/list"; // tạo file templates/admin/review/list.html
    }

    // Xem chi tiết review
    @GetMapping("/admin/reviews/{id}")
    public String reviewDetail(@PathVariable int id, Model model) {
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isPresent()) {
            model.addAttribute("review", review.get());
            return "admin/review/detail"; // tạo file templates/admin/review/detail.html
        }
        return "redirect:/admin/reviews";
    }

    // Duyệt review
    @PostMapping("/admin/reviews/{id}/approve")
    public String approveReview(@PathVariable int id) {
        reviewRepository.findById(id).ifPresent(review -> {
            review.setStatus("approved");
            reviewRepository.save(review);
        });
        return "redirect:/admin/reviews";
    }

    // Từ chối review
    @PostMapping("/admin/reviews/{id}/reject")
    public String rejectReview(@PathVariable int id) {
        reviewRepository.findById(id).ifPresent(review -> {
            review.setStatus("rejected");
            reviewRepository.save(review);
        });
        return "redirect:/admin/reviews";
    }

    // ==========================
    // USER: Xem review được duyệt
    // ==========================
    @GetMapping("/user/reviews/{itemId}")
    public String viewApprovedReviews(@PathVariable int itemId, Model model) {
        List<Review> reviews = reviewRepository.findByItemIdAndStatus(itemId, "approved");
        model.addAttribute("reviews", reviews);
        return "user/review/list"; // tạo file templates/user/review/list.html
    }

    // ==========================
    // USER: Gửi review mới
    // ==========================
    @PostMapping("/user/reviews/{itemId}/add")
    public String addReview(@PathVariable int itemId,
                            @RequestParam int userId,
                            @RequestParam int rating,
                            @RequestParam String comment) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Review review = new Review();
            review.setItemId(itemId);
            review.setRating(rating);
            review.setComment(comment);
            review.setCreatedBy(user.get());
            review.setCreatedAt(LocalDateTime.now());
            review.setStatus("pending"); // mặc định khi tạo review
            reviewRepository.save(review);
        }
        return "redirect:/user/reviews/" + itemId;
    }
}
