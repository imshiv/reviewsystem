package com.example.review.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class S3ServiceTest {

    private S3Client s3Client;
    private S3Service s3Service;

    @BeforeEach
    public void setup() {
        s3Client = mock(S3Client.class);
        s3Service = new S3Service(s3Client);
    }

    @Test
    public void testListNewFiles() {
        ListObjectsV2Response response = ListObjectsV2Response.builder()
            .contents(Collections.emptyList())
            .build();
        when(s3Client.listObjectsV2(any(ListObjectsV2Request.class))).thenReturn(response);

        var result = s3Service.listNewFiles("reviews/");
        assertTrue(result.isEmpty());
    }
}
