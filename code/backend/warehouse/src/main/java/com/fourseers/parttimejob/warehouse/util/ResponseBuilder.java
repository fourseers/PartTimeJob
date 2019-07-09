package com.fourseers.parttimejob.warehouse.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

    public static ResponseEntity<JSONObject> build(HttpStatus status, JSONObject data, String message) {
        JSONObject response = new JSONObject();
        response.put("status", status.value());
        response.put("message", message);
        if (data != null) {
            response.put("data", data);
        }

        if (status.value() == 400) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    public static ResponseEntity<JSONObject> build(int status, JSONObject data, String message) {
        HttpStatus httpStatus = HttpStatus.resolve(status);
        if(httpStatus == null)
            return null;
        return build(httpStatus, data, message);
    }
}
