package com.example.review.service;

import com.example.review.model.Review;
import com.example.review.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class ReviewProcessorTest {

    @Mock private S3Service s3Service;
    @Mock private ReviewRepository reviewRepository;
    @InjectMocks private ReviewProcessor processor;

    private final String jsonReview = "{\"reviewId\": \"r1\", \"author\": \"John\", \"content\": \"Good stay\", \"rating\": 4, \"date\": \"2023-07-18T12:00:00\", \"source\": \"agoda\"}";

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessNewReviews_savesValidReview() throws Exception {
        S3Object s3Object = S3Object.builder().key("reviews/file1.jl").build();
        when(s3Service.listNewFiles("reviews/")).thenReturn(List.of(s3Object));
        when(reviewRepository.existsByReviewId("reviews/file1.jl")).thenReturn(false);
        InputStream inputStream = new ByteArrayInputStream(jsonReview.getBytes());
        when(s3Service.getFileContent("reviews/file1.jl")).thenReturn(inputStream);

        processor.processNewReviews();

        ArgumentCaptor<Review> captor = ArgumentCaptor.forClass(Review.class);
        verify(reviewRepository, atLeastOnce()).save(captor.capture());
        assert "r1".equals(captor.getValue().getReviewId());
    }

    @Test
    void testProcessNewReviews_skipsAlreadyProcessed() {
        S3Object s3Object = S3Object.builder().key("reviews/file2.jl").build();
        when(s3Service.listNewFiles("reviews/")).thenReturn(List.of(s3Object));
        when(reviewRepository.existsByReviewId("reviews/file2.jl")).thenReturn(true);

        processor.processNewReviews();

        verify(s3Service, never()).getFileContent("reviews/file2.jl");
        verify(reviewRepository, never()).save(any());
    }
}
