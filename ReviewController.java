package com.example.review.controller;

import com.example.review.service.ReviewProcessor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewProcessor processor;

    public ReviewController(ReviewProcessor processor) {
        this.processor = processor;
    }

    @PostMapping("/process")
    public String triggerProcessing() {
        processor.processNewReviews();
        return "Processing triggered.";
    }
}
