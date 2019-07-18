package com.fourseers.parttimejob.common.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

    public static ResponseEntity<Response<JSONObject>> build(int status, JSONObject data, String message) {
        HttpStatus httpStatus = HttpStatus.resolve(status);
        if(httpStatus == null)
            return null;
        return build(httpStatus, data, message);
    }

    public static ResponseEntity<Response<JSONObject>> build(HttpStatus status, JSONObject data) {
        return build(status, data, status.getReasonPhrase());
    }

    public static ResponseEntity<Response<JSONObject>> build(HttpStatus status) {
        return build(status, null, status.getReasonPhrase());
    }

    public static <T> ResponseEntity<Response<T>> build(HttpStatus status, T data, String message) {

        Response<T> response = new Response<>(data, status.value(), message);

        return new ResponseEntity<>(response, status);
    }

    public static <T> ResponseEntity<Response<T>> build(HttpStatus status, T data) {
        return build(status, data, status.getReasonPhrase());
    }

    public static <T> ResponseEntity<Response<T>> buildEmpty(HttpStatus status) {

        Response<T> response = new Response<>(null, status.value(), status.getReasonPhrase());

        return new ResponseEntity<>(response, status);
    }
}
