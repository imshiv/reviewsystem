package com.example.review.repository;

import com.example.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, String> {
    boolean existsByReviewId(String reviewId);
}
