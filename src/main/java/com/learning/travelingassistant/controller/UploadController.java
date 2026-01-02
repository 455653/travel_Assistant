
package com.learning.travelingassistant.controller;

import com.learning.travelingassistant.common.Result;
import com.learning.travelingassistant.util.AliOssUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/image")
    public Result<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return Result.error("请选择要上传的文件");
            }

            long maxSize = 5 * 1024 * 1024;
            if (file.getSize() > maxSize) {
                return Result.error("文件大小不能超过 5MB");
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return Result.error("只能上传图片文件");
            }

            logger.info("开始上传图片: {}, 大小: {} bytes", file.getOriginalFilename(), file.getSize());

            String imageUrl = aliOssUtil.upload(file);

            logger.info("图片上传成功: {}", imageUrl);
            return Result.success(imageUrl);

        } catch (IOException e) {
            logger.error("图片上传失败: {}", e.getMessage(), e);
            return Result.error("图片上传失败: " + e.getMessage());
        }
    }
}