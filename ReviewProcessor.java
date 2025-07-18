package com.example.review.service;

import com.example.review.model.Review;
import com.example.review.repository.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service

@Autowired
private KafkaReviewProducer kafkaProducer;

public class ReviewProcessor {

    private final ReviewRepository repository;
    private final S3Service s3Service;

    public ReviewProcessor(ReviewRepository repository, S3Service s3Service) {
        this.repository = repository;
        this.s3Service = s3Service;
    }

    public void processNewReviews() {
        List<S3Object> files = s3Service.listNewFiles("reviews/");
        for (S3Object file : files) {
            if (!repository.existsByReviewId(file.key())) {
                try (InputStream is = s3Service.getFileContent(file.key());
                     BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                    reader.lines().forEach(line -> {
                        try {
                            Review review = parseJsonLine(line);
                            repository.save(review);
                            kafkaProducer.sendReview(review);
                        } catch (Exception e) {
                            // log and skip
                        }
                    });
                } catch (Exception ex) {
                    // handle error
                }
            }
        }
    }

    private Review parseJsonLine(String line) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(line, Review.class);
    }
}
