package com.example.demo.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByItemIdAndStatus(int itemId, String status);
}
