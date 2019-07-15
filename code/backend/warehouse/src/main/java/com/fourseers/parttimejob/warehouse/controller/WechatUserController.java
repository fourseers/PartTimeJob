package com.fourseers.parttimejob.warehouse.controller;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.warehouse.entity.WechatUser;
import com.fourseers.parttimejob.warehouse.service.WechatUserService;
import com.fourseers.parttimejob.warehouse.util.Response;
import com.fourseers.parttimejob.warehouse.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class WechatUserController {

    @Autowired
    private WechatUserService wechatUserService;

    @GetMapping("/info")
    public ResponseEntity<Response<JSONObject>> getUserInfo(@RequestHeader("x-internal-token") String internalToken) {
        WechatUser user = wechatUserService.findByInternalToken(internalToken);
        if(user == null)
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST);

        JSONObject resp = new JSONObject().fluentPut("info", wechatUserService.getUserInfo(user));
        return ResponseBuilder.build(HttpStatus.OK, resp);
    }

    @PostMapping("/info")
    public ResponseEntity<Response<JSONObject>> updateUserInfo(
            @RequestBody(required = true) JSONObject userInfo,
            @RequestHeader("x-internal-token") String internalToken) {
        WechatUser user = wechatUserService.findByInternalToken(internalToken);
        if(user == null)
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST);
        if(wechatUserService.updateUserInfo(user, userInfo))
            return ResponseBuilder.build(HttpStatus.OK);
        else
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "Invalid tag list, check your tag ids.");
    }
}
