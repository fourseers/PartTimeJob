package com.fourseers.parttimejob.arrangement.controller;


import com.fourseers.parttimejob.arrangement.dto.CheckinDto;
import com.fourseers.parttimejob.arrangement.dto.CheckoutDto;
import com.fourseers.parttimejob.arrangement.service.CheckService;
import com.fourseers.parttimejob.arrangement.service.WechatUserService;
import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.common.util.Response;
import com.fourseers.parttimejob.common.util.ResponseBuilder;
import com.fourseers.parttimejob.common.util.UserDecoder;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/user")
public class WechatCheckController {

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private CheckService checkService;

    @Value("${app.wechat_user_prefix}")
    private String WECHAT_USER_PREFIX;

    @ApiOperation(value = "Wechat employee check in")
    @PostMapping(value = "/checkin")
    public ResponseEntity<Response<Void>> checkIn(
            @ApiParam @RequestBody CheckinDto params,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String token ) {
        if(!UserDecoder.isWechatUser(token, WECHAT_USER_PREFIX))
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        WechatUser user = wechatUserService.getUserByOpenid(
                UserDecoder.getWechatUserOpenid(token, WECHAT_USER_PREFIX));
        if(user == null)
            return ResponseBuilder.buildEmpty(FORBIDDEN);

        try {
            if(checkService.checkIn(user, params))
                return ResponseBuilder.buildEmpty(OK);
            else
                return ResponseBuilder.buildEmpty(BAD_REQUEST);
        } catch (RuntimeException e) {
            return ResponseBuilder.build(BAD_REQUEST, null, e.getMessage());
        } catch (Exception e) {
            return ResponseBuilder.build(INTERNAL_SERVER_ERROR, null, e.getMessage());
        }
    }

    @ApiOperation(value = "Wechat employee check out")
    @PostMapping(value = "/checkout")
    public ResponseEntity<Response<Void>> checkIn(
            @ApiParam @RequestBody CheckoutDto params,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String token ) {
        if(!UserDecoder.isWechatUser(token, WECHAT_USER_PREFIX))
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        WechatUser user = wechatUserService.getUserByOpenid(
                UserDecoder.getWechatUserOpenid(token, WECHAT_USER_PREFIX));
        if(user == null)
            return ResponseBuilder.buildEmpty(FORBIDDEN);

        try {
            if(checkService.checkOut(user, params))
                return ResponseBuilder.buildEmpty(OK);
            else
                return ResponseBuilder.buildEmpty(BAD_REQUEST);
        } catch (RuntimeException e) {
            return ResponseBuilder.build(BAD_REQUEST, null, e.getMessage());
        } catch (Exception e) {
            return ResponseBuilder.build(INTERNAL_SERVER_ERROR, null, e.getMessage());
        }
    }

}
