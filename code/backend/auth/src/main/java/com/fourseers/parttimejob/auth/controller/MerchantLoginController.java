package com.fourseers.parttimejob.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.auth.entity.MerchantUser;
import com.fourseers.parttimejob.auth.service.MerchantUserService;
import com.fourseers.parttimejob.auth.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "merchant")
public class MerchantLoginController {

    @Autowired
    private OAuth oAuth;

    @Autowired
    private MerchantUserService merchantUserService;

    @PostMapping("/register")
    public ResponseEntity<JSONObject> register(
            @RequestBody JSONObject body, @RequestHeader("Authorization") String basicAuth) {
        String username = body.getString("username");
        String password = body.getString("password");
        if(username == null || username.isEmpty())
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "Empty username.");
        if(password == null || password.isEmpty())
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "Empty password.");

        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setUsername(username);
        merchantUser.setPassword(password);
        if(merchantUserService.register(merchantUser)) {
            JSONObject response = oAuth.getToken(username, password, "password", basicAuth);
            return ResponseBuilder.build(HttpStatus.OK, response, "OK");
            } else {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "User exists.");
        }

    }

    @PostMapping("/login")
    public ResponseEntity<JSONObject> login(
            @RequestBody JSONObject body, @RequestHeader("Authorization") String basicAuth) {
        String username = body.getString("username");
        String password = body.getString("password");

        JSONObject response = oAuth.getToken(username, password, "password", basicAuth);
        if(response.getString("access_token") != null)
            return ResponseBuilder.build(HttpStatus.OK, response, "OK");
        else
            return ResponseBuilder.build(HttpStatus.UNAUTHORIZED, null, null);
    }
}
