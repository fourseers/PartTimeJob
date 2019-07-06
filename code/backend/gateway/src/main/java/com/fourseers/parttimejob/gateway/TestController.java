package com.fourseers.parttimejob.gateway;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public JSONObject getToken(@RequestParam String username, @RequestParam String password) {
        return authService.getToken(
                username,
                password,
                "password"
        );
    }
}
