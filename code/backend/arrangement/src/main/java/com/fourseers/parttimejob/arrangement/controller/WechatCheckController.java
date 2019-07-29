package com.fourseers.parttimejob.arrangement.controller;


import com.fourseers.parttimejob.arrangement.dao.impl.CheckStatusDto;
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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

import static org.springframework.http.HttpStatus.*;

@RestController
@Validated
@RequestMapping("/user")
public class WechatCheckController {

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private CheckService checkService;

    @Value("${app.wechat_user_prefix}")
    private String WECHAT_USER_PREFIX;

    @ApiOperation(value = "Wechat employee check user info")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All parameters valid, " +
                    "return workDate, expected check times and acutal check times."),
            @ApiResponse(code = 400, message = "Invalid job / user not working today.")
    })
    @GetMapping(value = "check-status")
    public ResponseEntity<Response<CheckStatusDto>> checkStatus(
            @RequestParam("job_id") @Positive Integer jobId,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String token
    ) {
        if(!UserDecoder.isWechatUser(token, WECHAT_USER_PREFIX))
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        WechatUser user = wechatUserService.getUserByOpenid(
                UserDecoder.getWechatUserOpenid(token, WECHAT_USER_PREFIX));
        if(user == null)
            return ResponseBuilder.buildEmpty(FORBIDDEN);

        try {
            return ResponseBuilder.build(OK, checkService.getCheckStatusForToday(user, jobId));
        } catch (RuntimeException e) {
            return ResponseBuilder.build(BAD_REQUEST, null, e.getMessage());
        } catch (Exception e) {
            return ResponseBuilder.build(INTERNAL_SERVER_ERROR, null, e.getMessage());
        }
    }

    @ApiOperation(value = "Wechat employee check in")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Check in success"),
            @ApiResponse(code = 400, message = "Check in failed for various reasons( invalid time, geo location, etc)")
    })
    @PostMapping(value = "/checkin")
    public ResponseEntity<Response<Void>> checkIn(
            @ApiParam @RequestBody @Validated CheckinDto params,
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
    @ApiResponses({
            @ApiResponse(code = 200, message = "Check out success"),
            @ApiResponse(code = 400, message = "Check out failed for various reasons( invalid time, geo location, etc)")
    })
    @PostMapping(value = "/checkout")
    public ResponseEntity<Response<Void>> checkIn(
            @ApiParam @RequestBody @Validated CheckoutDto params,
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
