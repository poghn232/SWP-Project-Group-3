package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review") // tên table trong DB
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;

    private int itemId;

    private int rating;

    private String comment;

    private LocalDateTime createdAt;

    // Liên kết nhiều Review về một User
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    // Trạng thái review (pending, approved, rejected,...)
    private String status;

    public Review() {
        this.createdAt = LocalDateTime.now();
        this.status = "pending"; // mặc định khi tạo review
    }

    public Review(int itemId, int rating, String comment, User createdBy) {
        this.itemId = itemId;
        this.rating = rating;
        this.comment = comment;
        this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now();
        this.status = "pending"; // mặc định khi tạo review
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
