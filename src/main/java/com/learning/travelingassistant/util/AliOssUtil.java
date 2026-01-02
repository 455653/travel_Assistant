
package com.learning.travelingassistant.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
public class AliOssUtil {

    private static final Logger logger = LoggerFactory.getLogger(AliOssUtil.class);

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.region}")
    private String region;

    @Value("${aliyun.oss.access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.oss.access-key-secret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucket-name}")
    private String bucketName;

    public String upload(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("上传文件不能为空");
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".") 
            ? originalFilename.substring(originalFilename.lastIndexOf(".")) 
            : "";
        
        String objectName = "comments/" + UUID.randomUUID().toString() + extension;

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try (InputStream inputStream = file.getInputStream()) {
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                bucketName, 
                objectName, 
                inputStream
            );

            PutObjectResult result = ossClient.putObject(putObjectRequest);
            logger.info("文件上传成功: {}, ETag: {}", objectName, result.getETag());

            String url = buildPublicUrl(bucketName, endpoint, objectName);
            logger.info("生成访问 URL: {}", url);
            
            return url;

        } catch (Exception e) {
            logger.error("文件上传失败: {}", e.getMessage(), e);
            throw new IOException("文件上传失败: " + e.getMessage(), e);
        } finally {
            ossClient.shutdown();
        }
    }

    private String buildPublicUrl(String bucketName, String endpoint, String objectKey) {
        String endpointWithoutProtocol = endpoint.replace("https://", "").replace("http://", "");
        return String.format("https://%s.%s/%s", bucketName, endpointWithoutProtocol, objectKey);
    }
}