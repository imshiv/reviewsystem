package com.example.review;

import com.example.review.model.Review;
import com.example.review.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void testSaveAndRetrieveReview() {
        Review review = new Review();
        review.setId("123");
        review.setReviewerName("Alice");
        review.setReviewText("Great stay!");
        review.setRating(5);

        reviewRepository.save(review);
        Review retrieved = reviewRepository.findById("123").orElse(null);

        assertNotNull(retrieved);
        assertEquals("Alice", retrieved.getReviewerName());
    }
}
