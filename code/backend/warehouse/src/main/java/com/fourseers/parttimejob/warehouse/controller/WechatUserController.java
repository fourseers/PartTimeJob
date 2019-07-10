package com.fourseers.parttimejob.warehouse.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.warehouse.entity.WechatUser;
import com.fourseers.parttimejob.warehouse.service.WechatUserService;
import com.fourseers.parttimejob.warehouse.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class WechatUserController {

    @Autowired
    private WechatUserService wechatUserService;

    @GetMapping("/info")
    public ResponseEntity<JSONObject> getUserInfo(@RequestHeader("x-internal-token") String internalToken) {
        WechatUser user = wechatUserService.findByInternalToken(internalToken);
        if(user == null)
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST);

        JSONObject resp = new JSONObject().fluentPut("info", wechatUserService.getUserInfo(user));
        return ResponseBuilder.build(HttpStatus.OK, resp);
    }

    @PostMapping("/info")
    public ResponseEntity<JSONObject> updateUserInfo(@RequestHeader("x-internal-token") String internalToken) {
        WechatUser user = wechatUserService.findByInternalToken(internalToken);
        if(user == null)
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST);


    }
}
