package com.fourseers.parttimejob.gateway;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "auth")
public interface AuthService {

    @PostMapping("/oauth/token")
    JSONObject getToken(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam("grant_type") String grantType);

    @GetMapping("/oauth/check_token")
    JSONObject checkToken(@RequestParam("token") String accessToken,
                          @RequestHeader("Authorization") String basicAuth);
}
