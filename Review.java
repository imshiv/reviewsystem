package com.example.review.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Review {
    @Id
    private String reviewId;
    private String author;
    private String content;
    private int rating;
    private LocalDateTime date;
    private String source;

    // Getters and setters
}
