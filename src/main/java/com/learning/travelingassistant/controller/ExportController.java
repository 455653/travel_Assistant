
package com.learning.travelingassistant.controller;

import com.learning.travelingassistant.service.ExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/api/export")
public class ExportController {

    private static final Logger logger = LoggerFactory.getLogger(ExportController.class);

    @Autowired
    private ExportService exportService;

    @PostMapping("/word")
    public ResponseEntity<byte[]> exportToWord(@RequestBody Map<String, String> requestData) {
        try {
            String title = requestData.get("title");
            String content = requestData.get("content");

            if (title == null || title.isEmpty() || content == null || content.isEmpty()) {
                logger.warn("导出请求参数不完整");
                return ResponseEntity.badRequest().build();
            }

            logger.info("开始导出 Word 文档: {}", title);

            byte[] wordBytes = exportService.exportGuideToWord(title, content);

            String filename = URLEncoder.encode(title + ".docx", StandardCharsets.UTF_8)
                    .replace("+", "%20");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
            headers.setContentDispositionFormData("attachment", filename);
            headers.setContentLength(wordBytes.length);

            logger.info("Word 文档导出成功: {}, 大小: {} 字节", title, wordBytes.length);

            return new ResponseEntity<>(wordBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            logger.error("导出 Word 文档失败: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}