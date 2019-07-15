package com.fourseers.parttimejob.auth.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.common.util.Response;
import com.fourseers.parttimejob.common.util.ResponseBuilder;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalFeignExceptionHandler {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Response<JSONObject>> handleFeignStatusException(FeignException e, HttpServletResponse response) {
        if(e.contentUTF8() != null) {
            // a 4xx error
            response.setStatus(e.status());
            String message = HttpStatus.valueOf(e.status()).getReasonPhrase();
            return ResponseBuilder.build(e.status(), JSON.parseObject(e.contentUTF8()), message);
        } else {
            response.setStatus(500);
            return ResponseBuilder.build(500, null, "Internal server error.");
        }
    }
}
