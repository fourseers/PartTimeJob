package com.fourseers.parttimejob.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.auth.service.WechatUserService;
import com.fourseers.parttimejob.auth.util.Pair;
import com.fourseers.parttimejob.common.entity.Etc;
import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.common.util.Response;
import com.fourseers.parttimejob.common.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "wechat")
public class WechatLoginController {

    @Value("${wechat.appid}")
    private String APP_ID;

    @Value("${wechat.appsecret}")
    private String APP_SECRET;

    @Value("${app.wechat_user_prefix}")
    private String WECHAT_USER_PREFIX;

    @Value("${app.wechat_password_placeholder}")
    private String WECHAT_PASSWD_PLACEHOLDER;

    @Autowired
    private Wechat wechat;

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    OAuth oauth;

    private Pair<String, WechatUser> getWechatUser(JSONObject reqObject) {
        String token = (String) reqObject.get("token");
        JSONObject respObject = JSON.parseObject(wechat.auth(APP_ID, APP_SECRET, token, "authorization_code"));
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

    private ResponseEntity<Response<JSONObject>> oauthResult(String openid, String basicAuth) {

        JSONObject data = oauth.getToken(WECHAT_USER_PREFIX + openid, WECHAT_PASSWD_PLACEHOLDER,"password", basicAuth);

        if (data == null) {
            return ResponseBuilder.build(400, null, "unhandled error");
        }

        return ResponseBuilder.build(200, data, "success");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Response<JSONObject>> login(@RequestBody JSONObject body, @RequestHeader("Authorization") String basicAuth) {
        Pair<String, WechatUser> result = getWechatUser(body);

        if (result == null) {
            return ResponseBuilder.build(400, null, "invalid code");
        }

        WechatUser user = result.getSecond();

        // TODO: leave OAuth to check whether user exist, reduce one db query
        if (user == null) {
            return ResponseBuilder.build(400, null, "user not exist");
        }

        return oauthResult(user.getOpenid(), basicAuth);

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Response<JSONObject>> register(@RequestBody JSONObject body, @RequestHeader("Authorization") String basicAuth) {
        Pair<String, WechatUser> result = getWechatUser(body);

        if (result == null) {
            return ResponseBuilder.build(400, null, "invalid code");
        }

        String openid = result.getFirst();
        WechatUser user = result.getSecond();

        if (user != null) {
            return ResponseBuilder.build(400, null, "user exist");
        }

        user = new WechatUser();
        user.setOpenid(openid);
        user.setName(body.getString("name"));
        user.setGender(body.getBoolean("gender"));
        user.setIdentity(body.getString("identity"));
        user.setPhone(body.getString("phone"));
        user.setCountry(body.getString("country"));
        user.setCity(body.getString("city"));
        user.setEducation(Etc.Education.fromName(body.getString("education")));
        wechatUserService.save(user);

        return oauthResult(user.getOpenid(), basicAuth);
    }

}
