package com.example.demo.controller.ReviewController;

import com.example.demo.model.Review;
import com.example.demo.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    // ==========================
    // Danh sách review
    // ==========================
    @GetMapping("/admin/manageReview")
    public String listReviews(Model model) {
        List<Review> reviews = reviewRepository.findAll();
        model.addAttribute("reviews", reviews);
        return "admin/manageReview/listreview"; // templates/admin/manageReview/listreview.html
    }

    // ==========================
    // Chi tiết review
    // ==========================
    @GetMapping("/admin/manageReview/{id}")
    public String reviewDetail(@PathVariable int id, Model model) {
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isPresent()) {
            model.addAttribute("review", review.get());
            return "admin/manageReview/reviewdetail"; // templates/admin/manageReview/reviewdetail.html
        }
        return "redirect:/admin/manageReview"; // nếu review không tồn tại
    }

    // ==========================
    // Duyệt review
    // ==========================
    @PostMapping("/admin/manageReview/{id}/approve")
    public String approveReview(@PathVariable int id) {
        reviewRepository.findById(id).ifPresent(review -> {
            review.setStatus("approved");
            reviewRepository.save(review);
        });
        return "redirect:/admin/manageReview";
    }

    // ==========================
    // Từ chối review
    // ==========================
    @PostMapping("/admin/manageReview/{id}/reject")
    public String rejectReview(@PathVariable int id) {
        reviewRepository.findById(id).ifPresent(review -> {
            review.setStatus("rejected");
            reviewRepository.save(review);
        });
        return "redirect:/admin/manageReview";
    }
}
