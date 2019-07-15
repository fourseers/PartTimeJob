package com.fourseers.parttimejob.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Response<T> {
    private T data;
    private int status;
    private String message;

    public Response(T data, int status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
