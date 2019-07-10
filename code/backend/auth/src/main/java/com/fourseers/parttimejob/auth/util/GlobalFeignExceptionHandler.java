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
        response.setStatus(e.status());
        HttpStatus status = HttpStatus.resolve(e.status());
        String message = status == null ? "" : status.getReasonPhrase();
        int value = status == null ? 400 : status.value();
        String content = e.contentUTF8();
        if(content == null)
            content = "{'error': 'invalid_grant', 'error_desciption': 'Incorrect username or password'}";
        return ResponseBuilder.build(value, JSON.parseObject(content), message);
    }
}
