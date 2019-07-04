package com.fourseers.parttimejob.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.auth.entity.WechatUser;
import com.fourseers.parttimejob.auth.service.WechatUserService;
import com.fourseers.parttimejob.auth.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class WechatLoginController {

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.appsecret}")
    private String appsecret;

    private Wechat wechat;

    @Autowired
    private WechatUserService wechatUserService;

    @PostConstruct
    public void init() {
        wechat = Wechat.connect();
    }

    protected void setWechat(Wechat wechat) {
        this.wechat = wechat;
    }

    private Pair<String, WechatUser> getWechatUser(JSONObject reqObject) {
        String token = (String) reqObject.get("token");
        JSONObject respObject = wechat.auth(appid, appsecret, token, "authorization_code");
        String sessionKey;
        String openid;
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
            return "invalid token";
        }

        WechatUser user = result.getSecond();

        if (user != null) {
            // TODO: return OAuth Token
            return "success";
        } else {
            // TODO: ask user to register
            return "not exist";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody JSONObject body) {
        Pair<String, WechatUser> result = getWechatUser(body);
        if (result == null) {
            // TODO: Temporary token incorrect
            return "invalid token";
        }

        String openid = result.getFirst();
        WechatUser user = result.getSecond();

        if (user != null) {
            // TODO: user exist
            return "user exist";
        } else {
            user = new WechatUser();
            user.setOpenid(openid);
            user.setName(body.getString("name"));
            user.setGender(body.getBoolean("gender"));
            user.setIdentity(body.getString("identity"));
            user.setPhone(body.getString("phone"));
            user.setCountry(body.getString("country"));
            user.setCity(body.getString("city"));
            user.setEducation(body.getString("education"));
            wechatUserService.save(user);
            // TODO: return success
            return "success";
        }
    }

}
