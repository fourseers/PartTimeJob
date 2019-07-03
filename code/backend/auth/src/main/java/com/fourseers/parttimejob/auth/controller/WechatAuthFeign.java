package com.fourseers.parttimejob.auth.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "wechatAuth", url = "https://api.weixin.qq.com/sns/")
public interface WechatAuthFeign {
    @RequestMapping(value = "jscode2session", method = RequestMethod.GET)
    String auth(@RequestParam("appid") String appid,
                @RequestParam("secret") String appsecret,
                @RequestParam("js_code") String jsCode,
                @RequestParam("grant_type") String grantType);
}
