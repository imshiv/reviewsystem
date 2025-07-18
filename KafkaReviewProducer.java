package com.example.review.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.review.model.Review;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaReviewProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public KafkaReviewProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendReview(Review review) {
        try {
            kafkaTemplate.send("reviews", mapper.writeValueAsString(review));
        } catch (Exception e) {
            // log error
        }
    }
}
