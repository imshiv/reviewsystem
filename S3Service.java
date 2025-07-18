package com.example.review.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;
import java.util.List;

@Service
public class S3Service {

    private final S3Client s3;

    @Value("${aws.bucket}")
    private String bucketName;

    public S3Service(S3Client s3) {
        this.s3 = s3;
    }

    public List<S3Object> listNewFiles(String prefix) {
        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix(prefix)
                .build();
        ListObjectsV2Response result = s3.listObjectsV2(request);
        return result.contents();
    }

    public InputStream getFileContent(String key) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        return s3.getObject(request);
    }
}
