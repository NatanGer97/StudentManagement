package com.backend.StudentManagement.Services;

import com.amazonaws.*;
import com.amazonaws.services.s3.*;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.io.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

@Service
public class AwsService {
    private final Logger logger = LoggerFactory.getLogger(AwsService.class);

    @Value("${bucket.url}")
    private String bucket;

    @Autowired
    private AmazonS3 s3Client;

    public String getBucketUrl() {
        return bucket;
    }

    public void setBucketUrl(String bucket) {
        this.bucket = bucket;
    }

    public void putInBucket(MultipartFile file, String path) {

        try {
            Path destinationFile = Files.createTempFile("file" + UUID.randomUUID(), null);
            FileUtils.copyInputStreamToFile(file.getInputStream(), destinationFile.toFile());
            saveAndSend(destinationFile, path);
            Files.deleteIfExists(destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Error uploading file to bucket: " + bucket + "/ " + path, e);
        }
    }

    private void saveAndSend(Path uploadFile, String destPath) {
        PutObjectRequest request = new PutObjectRequest(bucket, destPath, uploadFile.toFile());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/png");
        request.setMetadata(metadata);
        s3Client.putObject(request);
    }

    public String generateLink(String fileUrl) {
        return generateLink(bucket, fileUrl);
    }

    private String generateLink(String bucket, String fileUrl) {
        // Set the presigned URL to expire after one min.
        if (fileUrl == null) return null;
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60;
//        expiration.setTime(expTimeMillis);
        // Generate the pre-signed URL.
        return generateLink(bucket, fileUrl, expTimeMillis);


    }

    public String generateLink(String bucketName, String fileUrl, long expTimeMillis) {
        if (fileUrl.lastIndexOf(bucketName) >= 0) {
            fileUrl = fileUrl.substring(fileUrl.lastIndexOf(bucketName) + bucketName.length() + 1);
        }
        try {
            Date expiration = new Date();
            expiration.setTime(expTimeMillis);
            // Generate the pre-signed URL.
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, fileUrl)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);

            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
            return url.toString();
        } catch (AmazonServiceException e) {
            logger.error("Error generating link for file: " + fileUrl, e);
            // if error, return null
            return null;
        }

    }
}
