package com.example.review.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaReviewConsumer {

    @KafkaListener(topics = "reviews", groupId = "review-consumer")
    public void listen(String message) {
        System.out.println("Consumed review: " + message);
    }
}
