package com.fourseers.parttimejob.auth.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalFeignExceptionHandler {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<JSONObject> handleFeignStatusException(FeignException e, HttpServletResponse response) {
        HttpStatus status = HttpStatus.resolve(e.status());
        String message = status == null ? "" : status.toString();
        return ResponseBuilder.build(e.status(), JSON.parseObject(e.contentUTF8()), message);
    }
}
