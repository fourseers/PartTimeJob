package com.fourseers.parttimejob.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.auth.entity.MerchantUser;
import com.fourseers.parttimejob.auth.service.MerchantUserService;
import com.fourseers.parttimejob.auth.util.ResponseBuilder;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "merchant")
public class MerchantLoginController {

    @Autowired
    private OAuth oAuth;

    @Autowired
    private MerchantUserService merchantUserService;

    // NOT controller
    private ResponseEntity<JSONObject> getOAuthResponse(String username, String password, String basicAuth) {
        JSONObject response = null;
        try {
            response = oAuth.getToken(username, password, "password", basicAuth);
        } catch (HystrixRuntimeException e) {
            // 401 or 400 error
            if(e.getCause() instanceof FeignException) {
                FeignException cause = (FeignException) e.getCause();
                HttpStatus returnStatus;
                try {
                    returnStatus = HttpStatus.valueOf(cause.status());
                    String message;
                    if(returnStatus == HttpStatus.BAD_REQUEST)
                        message = "Bad request, invalid username and password.";
                    else if(returnStatus == HttpStatus.UNAUTHORIZED)
                        message = "Unauthorized, check client credentials.";
                    else
                        message = returnStatus.getReasonPhrase();
                    return ResponseBuilder.build(returnStatus, null, message);
                } catch (IllegalArgumentException e1) {
                    // do nothing and leave response null
                }
            }
        } catch (Exception e) {
            // do nothing, leave response null, ignore it
        }
        if(response == null)
            return ResponseBuilder.build(HttpStatus.INTERNAL_SERVER_ERROR,
                    null, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return ResponseBuilder.build(HttpStatus.OK, response, "success");
    }

    private boolean checkParamsNotEmpty(String username, String password) {
        return username != null && !username.isEmpty() &&
                password != null && !password.isEmpty();
    }

    @PostMapping("/register")
    public ResponseEntity<JSONObject> register(
            @RequestBody JSONObject body, @RequestHeader("Authorization") String basicAuth) {
        String username = body.getString("username");
        String password = body.getString("password");
        if(!checkParamsNotEmpty(username, password))
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "Absence of params.");

        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setUsername(username);
        merchantUser.setPassword(password);
        if(merchantUserService.register(merchantUser)) {
            return getOAuthResponse(username, password, basicAuth);
        } else {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "User exists.");
        }

    }

    @PostMapping("/login")
    public ResponseEntity<JSONObject> login(
            @RequestBody JSONObject body, @RequestHeader("Authorization") String basicAuth) {
        String username = body.getString("username");
        String password = body.getString("password");
        if(!checkParamsNotEmpty(username, password))
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "Absence of params.");

        return getOAuthResponse(username, password, basicAuth);
    }
}
