package com.service.dida.global.util.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.service.dida.global.util.usecase.S3UseCase;
import java.io.IOException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3Service implements S3UseCase {
    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.s3.url}")
    private String url;

    @Override
    public String uploadImg(Long memberId, MultipartFile file, String type) throws IOException {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        s3Client = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(region)
            .build();
        String fileName = memberId + "/" + LocalDateTime.now() + "_" + file.getOriginalFilename();

        String filePath = "";
        if (type.equals("profile")) {
            filePath = "/profile";
        }

        ObjectMetadata metadata = new ObjectMetadata();
        s3Client.putObject(new PutObjectRequest(bucket + filePath, fileName, file.getInputStream(), metadata)
            .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        return url + filePath + "/" + fileName;
    }
}