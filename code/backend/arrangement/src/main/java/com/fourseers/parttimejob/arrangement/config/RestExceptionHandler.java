package com.fourseers.parttimejob.arrangement.config;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.common.util.Response;
import com.fourseers.parttimejob.common.util.ResponseBuilder;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response<Void>> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations())
            errors.add(violation.getMessage());
        return ResponseBuilder.build(HttpStatus.BAD_REQUEST,
                null, StringUtils.join(errors.toArray()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for(ObjectError error: ex.getBindingResult().getAllErrors())
            errors.add(error.getObjectName().concat(error.getDefaultMessage()));
        JSONObject response = new JSONObject()
                .fluentPut("status", HttpStatus.BAD_REQUEST.value())
                .fluentPut("message", StringUtils.join(errors.toArray()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
