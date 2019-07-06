package com.fourseers.parttimejob.warehouse.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {

    @GetMapping("test")
    public String getHello() {
        return "Hello world!";
    }

    @GetMapping("getUserName")
    public String getUsername(HttpServletRequest request) {
        return request.getHeader("x-internal-token");
    }
}
