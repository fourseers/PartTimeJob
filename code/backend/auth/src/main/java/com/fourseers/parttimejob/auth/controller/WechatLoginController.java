package com.fourseers.parttimejob.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.auth.entity.WechatUser;
import com.fourseers.parttimejob.auth.service.WechatUserService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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

    private Pair<String, WechatUser> getWechatUser(JSONObject reqObject) {
        String token = (String) reqObject.get("token");
        String resp = wechatAuthFeign.auth(appid, appsecret, token, "authorization_code");
        JSONObject respObject = (JSONObject) JSONObject.parse(resp);
        String sessionKey, openid;
        try {
            sessionKey = respObject.get("session_key").toString();
            openid = respObject.get("openid").toString();
        } catch (NullPointerException ex) {
            return null;
        }

        return new Pair<>(openid, wechatUserService.findByOpenid(openid));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody JSONObject body) {
        Pair<String, WechatUser> result = getWechatUser(body);
        if (result == null) {
            // TODO: Temporary token incorrect
            return "failed";
        }

        WechatUser user = result.getValue();

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
        Pair<String, WechatUser> result = getWechatUser(body);
        if (result == null) {
            // TODO: Temporary token incorrect
            return "failed";
        }

        String openid = result.getKey();
        WechatUser user = result.getValue();

        if (user != null) {
            // TODO: user exist
            return "failed";
        } else {
            user = new WechatUser();
            user.setOpenid(openid);
            user.setName((String) body.get("username"));
            wechatUserService.save(user);
            // TODO: return success
            return "success";
        }
    }

}
