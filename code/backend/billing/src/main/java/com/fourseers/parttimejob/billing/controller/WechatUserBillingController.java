package com.fourseers.parttimejob.billing.controller;

import com.fourseers.parttimejob.billing.dto.DrawMoneyDto;
import com.fourseers.parttimejob.billing.projection.UserWorkEntryProjection;
import com.fourseers.parttimejob.billing.service.BillingService;
import com.fourseers.parttimejob.billing.service.WechatUserService;
import com.fourseers.parttimejob.common.entity.Billing;
import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.common.util.Response;
import com.fourseers.parttimejob.common.util.ResponseBuilder;
import com.fourseers.parttimejob.common.util.UserDecoder;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.PositiveOrZero;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/user")
public class WechatUserBillingController {

    @Value("${app.wechat_user_prefix}")
    private String WECHAT_USER_PREFIX;

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private BillingService billingService;

    @GetMapping("/works")
    public ResponseEntity<Response<Page<UserWorkEntryProjection>>> getUserBills(
            @RequestParam(defaultValue = "0") Integer pageCount,
            @RequestHeader("x-internal-token") String token
    ) {
        if(!UserDecoder.isWechatUser(token, WECHAT_USER_PREFIX))
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        WechatUser user = wechatUserService.getUserByOpenid(
                UserDecoder.getWechatUserOpenid(token, WECHAT_USER_PREFIX));
        if(user == null)
            return ResponseBuilder.buildEmpty(FORBIDDEN);

        try {
            return ResponseBuilder.build(OK, billingService.getUserWork(user, pageCount));
        } catch(RuntimeException e) {
            return ResponseBuilder.build(BAD_REQUEST, null, e.getMessage());
        } catch(Exception e ) {
            return ResponseBuilder.build(INTERNAL_SERVER_ERROR, null, e.getMessage());
        }
    }

    @ApiOperation(value = "Get user payment history")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Invalid page number or runtime exception"),
            @ApiResponse(code = 403, message = "Invalid user token")
    })
    @GetMapping("/draw-history")
    public ResponseEntity<Response<Page<Billing>>> getDrawnBills(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer pageCount,
            @RequestHeader("x-internal-token") String token) {
        if(!UserDecoder.isWechatUser(token, WECHAT_USER_PREFIX))
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        WechatUser user = wechatUserService.getUserByOpenid(
                UserDecoder.getWechatUserOpenid(token, WECHAT_USER_PREFIX));
        if(user == null)
            return ResponseBuilder.buildEmpty(FORBIDDEN);

        try {
            return ResponseBuilder.build(OK, billingService.getDrawnBillings(user, pageCount));
        } catch(RuntimeException e) {
            return ResponseBuilder.build(BAD_REQUEST, null, e.getMessage());
        } catch(Exception e ) {
            return ResponseBuilder.build(INTERNAL_SERVER_ERROR, null, e.getMessage());
        }
    }

    @ApiOperation(value = "Get user balance")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 403, message = "Invalid user token.")})
    @GetMapping("/balance")
    public ResponseEntity<Response<Double>> getBalance(
            @RequestHeader("x-internal-token") String token) {
        if(!UserDecoder.isWechatUser(token, WECHAT_USER_PREFIX))
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        WechatUser user = wechatUserService.getUserByOpenid(
                UserDecoder.getWechatUserOpenid(token, WECHAT_USER_PREFIX));
        if(user == null)
            return ResponseBuilder.buildEmpty(FORBIDDEN);

        try {
            Double ret = billingService.getUserBalance(user);
            if(ret == null)
                return ResponseBuilder.build(INTERNAL_SERVER_ERROR, null, "API returned null.");
            return ResponseBuilder.build(OK, ret);
        } catch(RuntimeException e) {
            return ResponseBuilder.build(BAD_REQUEST, null, e.getMessage());
        } catch(Exception e ) {
            return ResponseBuilder.build(INTERNAL_SERVER_ERROR, null, e.getMessage());
        }
    }


    @ApiOperation(value = "Draw all money for wechat user.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "API invokation succeeded. Note that if user balance is zero, no money would be drawn."),
            @ApiResponse(code = 400, message =
                    "Drawing all money failed due to invalid param."),
            @ApiResponse(code = 403, message = "Invalid user token.")
    })
    @PostMapping("/drawAll")
    public ResponseEntity<Response<DrawMoneyDto>> drawAllMoney(
            @RequestHeader("x-internal-token") String token
    ) {
        if(!UserDecoder.isWechatUser(token, WECHAT_USER_PREFIX))
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        WechatUser user = wechatUserService.getUserByOpenid(
                UserDecoder.getWechatUserOpenid(token, WECHAT_USER_PREFIX));
        if(user == null)
            return ResponseBuilder.buildEmpty(FORBIDDEN);

        try {
            Double moneyDrew = billingService.drawAllMoney(user);
            if(moneyDrew == 0.0) {
                DrawMoneyDto drawMoneyDto = new DrawMoneyDto();
                drawMoneyDto.setPayment(0.0);
                drawMoneyDto.setSuccess(false);
                return ResponseBuilder.build(OK, drawMoneyDto, "No money to draw.");
            }
            else {
                DrawMoneyDto drawMoneyDto = new DrawMoneyDto();
                drawMoneyDto.setSuccess(true);
                drawMoneyDto.setPayment(moneyDrew);
                return ResponseBuilder.build(OK, drawMoneyDto);
            }
        } catch(RuntimeException e) {
            return ResponseBuilder.build(BAD_REQUEST, null, e.getMessage());
        } catch(Exception e ) {
            return ResponseBuilder.build(INTERNAL_SERVER_ERROR, null, e.getMessage());
        }
    }

}
