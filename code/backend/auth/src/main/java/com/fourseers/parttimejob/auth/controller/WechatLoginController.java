package com.fourseers.parttimejob.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.auth.entity.WechatUser;
import com.fourseers.parttimejob.auth.service.WechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;

@RestController
public class WechatLoginController {

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.appsecret}")
    private String appsecret;

    @Autowired
    WechatAuthFeign wechatAuthFeign;

    @Autowired
    WechatUserService wechatUserService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody JSONObject body) {
        String token = (String) body.get("token");
        String result = wechatAuthFeign.auth(appid, appsecret, token, "authorization_code");
        JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
        String sessionKey, openid;
        try {
            sessionKey = jsonObject.get("session_key").toString();
            openid = jsonObject.get("openid").toString();
        } catch (NullPointerException ex) {
            return "failed";
        }

        WechatUser user = wechatUserService.findByOpenid(openid);

        if (user != null) {
            // TODO: return OAuth Token
            return "success";
        } else {
            // TODO: ask user to register
            return "failed";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody JSONObject body) {
        String token = (String) body.get("token");
        String result = wechatAuthFeign.auth(appid, appsecret, token, "authorization_code");
        JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
        String sessionKey, openid;
        try {
            sessionKey = jsonObject.get("session_key").toString();
            openid = jsonObject.get("openid").toString();
        } catch (NullPointerException ex) {
            return "failed";
        }

        WechatUser user = wechatUserService.findByOpenid(openid);

        if (user != null) {
            // TODO: user exist
            return "failed";
        } else {
            user = new WechatUser();
            user.setOpenid(openid);
            user.setUsername((String) body.get("username"));
            wechatUserService.save(user);
            // TODO: return success
            return "success";
        }
    }

}
