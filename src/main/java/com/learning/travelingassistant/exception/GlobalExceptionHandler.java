package com.learning.travelingassistant.exception;

import com.learning.travelingassistant.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        e.printStackTrace();
        return Result.error("服务器内部错误: " + e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public Result<Void> handleNullPointerException(NullPointerException e) {
        e.printStackTrace();
        return Result.error(404, "请求的资源不存在");
    }
}
