package com.fourseers.parttimejob.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.auth.entity.WechatUser;
import com.fourseers.parttimejob.auth.service.WechatUserService;
import com.fourseers.parttimejob.auth.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WechatLoginController {

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.appsecret}")
    private String appsecret;

    @Value("${app.wechat_user_prefix}")
    private String wechatUserPrefix;

    @Autowired
    private Wechat wechat;

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    OAuth oauth;

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
    public ResponseEntity<JSONObject> login(@RequestBody JSONObject body) {
        Pair<String, WechatUser> result = getWechatUser(body);

        JSONObject response = new JSONObject();
        HttpStatus status = HttpStatus.OK;

        if (result == null) {
            response.put("status", 400);
            response.put("message", "invalid token");
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(response, status);
        }

        WechatUser user = result.getSecond();

        if (user != null) {
            response.put("status", 200);
            response.put("message", "success");
            response.put("data", oauth.getToken(wechatUserPrefix + user.getOpenid(), "", "password").getString("data"));
            return new ResponseEntity<>(response, status);
        } else {
            response.put("status", 400);
            response.put("message", "user not exist");
            return new ResponseEntity<>(response, status);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> register(@RequestBody JSONObject body) {
        Pair<String, WechatUser> result = getWechatUser(body);

        JSONObject response = new JSONObject();
        HttpStatus status = HttpStatus.OK;

        if (result == null) {
            response.put("status", 400);
            response.put("message", "invalid token");
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(response, status);
        }

        String openid = result.getFirst();
        WechatUser user = result.getSecond();

        if (user != null) {
            response.put("status", 400);
            response.put("message", "user exist");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
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
            response.put("status", 200);
            response.put("message", "success");
            response.put("data", oauth.getToken(wechatUserPrefix + user.getOpenid(), "", "password"));
            return new ResponseEntity<>(response, status);
        }
    }

}
