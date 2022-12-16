package com.backend.StudentManagement.Configuration;

import com.amazonaws.*;
import com.amazonaws.auth.*;
import com.amazonaws.regions.*;
import com.amazonaws.services.s3.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.core.env.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.*;
import java.security.*;
import java.util.concurrent.*;

@Configuration
public class AwsConfig {
    @Autowired
    private Environment env;

    private String secretKey;
    private String accessKey;

    @PostConstruct
    public void init() {
        this.accessKey = env.getRequiredProperty("amazon.aws.accesskey").trim();
        this.secretKey = env.getRequiredProperty("amazon.aws.secretkey").trim();
    }
    @Bean
    public AWSCredentials awsCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    @Bean
    public AmazonS3 amazonS3 ()
    {
        ClientConfiguration cf = new ClientConfiguration();
        cf.setMaxErrorRetry(5);
        cf.setConnectionTTL(TimeUnit.MINUTES.toMillis(5));
        cf.setMaxConnections(100);
        cf.setMaxErrorRetry(5);
        cf.setSignerOverride("AWSS3V4SignerType");
        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.EU_WEST_1)
//                .withRegion("eu-central-1")
                .withPathStyleAccessEnabled(true)
                .withClientConfiguration(cf)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials()))
                .build();
    }
}
