package com.fourseers.parttimejob.auth.controller;

import com.alibaba.fastjson.JSONObject;
import feign.Feign;
import feign.Logger;
import feign.Param;
import feign.RequestLine;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

public interface Wechat {
    @RequestLine("GET /sns/jscode2session?appid={appid}&secret={secret}&js_code={js_code}&grant_type={grant_type}")
    JSONObject auth(@Param("appid") String appid,
                    @Param("secret") String appsecret,
                    @Param("js_code") String jsCode,
                    @Param("grant_type") String grantType);

    static Wechat connect() {
        Decoder decoder = new GsonDecoder();
        Encoder encoder = new GsonEncoder();
        return Feign.builder()
                .encoder(encoder)
                .decoder(decoder)
                .logger(new Logger.ErrorLogger())
                .logLevel(Logger.Level.BASIC)
                .target(Wechat.class, "https://api.weixin.qq.com/");
    }
}
