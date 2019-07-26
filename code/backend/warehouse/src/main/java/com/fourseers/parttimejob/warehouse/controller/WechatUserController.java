package com.fourseers.parttimejob.warehouse.controller;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.common.util.Response;
import com.fourseers.parttimejob.common.util.ResponseBuilder;
import com.fourseers.parttimejob.common.util.UserDecoder;
import com.fourseers.parttimejob.warehouse.dto.CVDto;
import com.fourseers.parttimejob.warehouse.dto.NewCVDto;
import com.fourseers.parttimejob.warehouse.dto.ScoreDto;
import com.fourseers.parttimejob.warehouse.dto.UserShopDto;
import com.fourseers.parttimejob.warehouse.projection.CVBriefProjection;
import com.fourseers.parttimejob.warehouse.service.CVService;
import com.fourseers.parttimejob.warehouse.service.ShopService;
import com.fourseers.parttimejob.warehouse.service.WechatUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/user")
public class WechatUserController {

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private CVService cvService;

    @Autowired
    private ShopService shopService;

    @Value("${app.wechat_user_prefix}")
    private String WECHAT_USER_PREFIX;

    @GetMapping("/info")
    public ResponseEntity<Response<JSONObject>> getUserInfo(@RequestHeader("x-internal-token") String internalToken) {
        WechatUser user = wechatUserService.findByInternalToken(internalToken);
        if(user == null)
            return ResponseBuilder.build(BAD_REQUEST);

        JSONObject resp = new JSONObject().fluentPut("info", wechatUserService.getUserInfo(user));
        return ResponseBuilder.build(HttpStatus.OK, resp);
    }

    @PostMapping("/info")
    public ResponseEntity<Response<Void>> updateUserInfo(
            @RequestBody(required = true) JSONObject userInfo,
            @RequestHeader("x-internal-token") String internalToken) {
        WechatUser user = wechatUserService.findByInternalToken(internalToken);
        if(user == null)
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        if(wechatUserService.updateUserInfo(user, userInfo))
            return ResponseBuilder.buildEmpty(HttpStatus.OK);
        else
            return ResponseBuilder.build(BAD_REQUEST, null, "Invalid tag list, check your tag ids.");
    }

    @ApiOperation(value = "Get user cv list, each element contains CV id and CV title.")
    @GetMapping("/cv-list")
    public ResponseEntity<Response<List<CVBriefProjection>>> getUserCVList(
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String token) {
        if (!UserDecoder.isWechatUser(token, WECHAT_USER_PREFIX))
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        WechatUser user = wechatUserService.getUserByOpenid(
                UserDecoder.getWechatUserOpenid(token, WECHAT_USER_PREFIX));
        if (user == null)
            return ResponseBuilder.buildEmpty(FORBIDDEN);

        try {
            return ResponseBuilder.build(OK, cvService.getCVListByUser(user));
        } catch (RuntimeException e) {
            return ResponseBuilder.build(BAD_REQUEST, null, e.getMessage());
        } catch (Exception e) {
            return ResponseBuilder.build(INTERNAL_SERVER_ERROR, null, e.getMessage());
        }
    }

    @ApiOperation(value = "get one cv according to cvId")
    @ApiResponses({
            @ApiResponse(code = 200, message = "operation successful"),
            @ApiResponse(code = 400, message = "Invalid cv id or cv does no belong to user.")
    })
    @GetMapping("/cv")
    public ResponseEntity<Response<CVDto>> getCV(
            @ApiParam("cvId in guid format") @RequestParam("cv_id") String cvId,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String token
    ) {
        if (!UserDecoder.isWechatUser(token, WECHAT_USER_PREFIX))
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        WechatUser user = wechatUserService.getUserByOpenid(
                UserDecoder.getWechatUserOpenid(token, WECHAT_USER_PREFIX));
        if (user == null)
            return ResponseBuilder.buildEmpty(FORBIDDEN);
        try {
            return ResponseBuilder.build(OK, cvService.getCV(cvId));
        } catch (RuntimeException e) {
            return ResponseBuilder.build(BAD_REQUEST, null, e.getMessage());
        } catch (Exception e) {
            return ResponseBuilder.build(INTERNAL_SERVER_ERROR, null, e.getMessage());
        }
    }

    @ApiOperation(value = "update one cv according to given cv")
    @ApiResponses({
            @ApiResponse(code = 200, message = "operation successful"),
            @ApiResponse(code = 400, message = "Invalid cv id or cv does no belong to user or missing params.")
    })
    @PutMapping("/cv")
    public ResponseEntity<Response<Void>> updateCV(
            @ApiParam("Full modified cv") @Validated @RequestBody CVDto cvDto,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String token
    ) {
        if (!UserDecoder.isWechatUser(token, WECHAT_USER_PREFIX))
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        WechatUser user = wechatUserService.getUserByOpenid(
                UserDecoder.getWechatUserOpenid(token, WECHAT_USER_PREFIX));
        if (user == null)
            return ResponseBuilder.buildEmpty(FORBIDDEN);
        try {
            if(cvService.updateCV(cvDto, user))
                return ResponseBuilder.buildEmpty(OK);
            else
                return ResponseBuilder.build(BAD_REQUEST, null, "Some check failed.");
        } catch (RuntimeException e) {
            return ResponseBuilder.build(BAD_REQUEST, null, e.getMessage());
        } catch (Exception e) {
            return ResponseBuilder.build(INTERNAL_SERVER_ERROR, null, e.getMessage());
        }
    }

    @ApiOperation(value = "add one new cv")
    @ApiResponses({
            @ApiResponse(code = 200, message = "operation successful"),
            @ApiResponse(code = 400, message = "Invalid cv id or cv does no belong to user or missing params.")
    })
    @PostMapping("/cv")
    public ResponseEntity<Response<Void>> addCV(
            @ApiParam("New cv, doesn't contain userId and cvId") @Validated @RequestBody NewCVDto newCvDto,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String token
    ) {
        if (!UserDecoder.isWechatUser(token, WECHAT_USER_PREFIX))
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        WechatUser user = wechatUserService.getUserByOpenid(
                UserDecoder.getWechatUserOpenid(token, WECHAT_USER_PREFIX));
        if (user == null)
            return ResponseBuilder.buildEmpty(FORBIDDEN);
        try {
            if(cvService.addCV(newCvDto, user))
                return ResponseBuilder.buildEmpty(OK);
            else
                return ResponseBuilder.build(BAD_REQUEST, null, "Some check failed.");
        } catch (RuntimeException e) {
            return ResponseBuilder.build(BAD_REQUEST, null, e.getMessage());
        } catch (Exception e) {
            return ResponseBuilder.build(INTERNAL_SERVER_ERROR, null, e.getMessage());
        }
    }

    @ApiOperation(value = "update one cv according to given cv")
    @ApiResponses({
            @ApiResponse(code = 200, message = "operation successful"),
            @ApiResponse(code = 400, message = "Invalid cv id or cv does no belong to user.")
    })
    @DeleteMapping("/cv")
    public ResponseEntity<Response<Void>> deleteCV(
            @ApiParam("id for cv to delete") @Validated @RequestParam("cv_id") String cvId,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String token
    ) {
        if (!UserDecoder.isWechatUser(token, WECHAT_USER_PREFIX))
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        WechatUser user = wechatUserService.getUserByOpenid(
                UserDecoder.getWechatUserOpenid(token, WECHAT_USER_PREFIX));
        if (user == null)
            return ResponseBuilder.buildEmpty(FORBIDDEN);
        try {
            if(cvService.deleteCV(cvId, user))
                return ResponseBuilder.buildEmpty(OK);
            else
                return ResponseBuilder.build(BAD_REQUEST, null, "Some check failed.");
        } catch (RuntimeException e) {
            return ResponseBuilder.build(BAD_REQUEST, null, e.getMessage());
        } catch (Exception e) {
            return ResponseBuilder.build(INTERNAL_SERVER_ERROR, null, e.getMessage());
        }
    }

    @ApiOperation(value = "Get detail for one shop.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful, return shop detail with avg score."),
            @ApiResponse(code = 400, message = "Invalid shop id")
    })
    @GetMapping("/shop")
    public ResponseEntity<Response<UserShopDto>> getShopDetail(
            @ApiParam @RequestParam("shop_id") int shopId,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String token) {
        if (!UserDecoder.isWechatUser(token, WECHAT_USER_PREFIX))
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        WechatUser user = wechatUserService.getUserByOpenid(
                UserDecoder.getWechatUserOpenid(token, WECHAT_USER_PREFIX));
        if (user == null)
            return ResponseBuilder.buildEmpty(FORBIDDEN);

        try {
            return ResponseBuilder.build(OK, shopService.getShopDetailWithAvgScore(user, shopId));
        } catch (RuntimeException e) {
            return ResponseBuilder.build(BAD_REQUEST, null, e.getMessage());
        } catch (Exception e) {
            return ResponseBuilder.build(INTERNAL_SERVER_ERROR, null, e.getMessage());
        }
    }

    @ApiOperation(value = "Add a score to a shop. " +
            "This operation either add a new score or modify the existing score.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful."),
            @ApiResponse(code = 400, message = "Invalid shop id or invalid score.")
    })
    @PostMapping("/shop/score")
    public ResponseEntity<Response<Void>> scoreShop(
            @ApiParam("parameters, contain shop id and score in int") @RequestBody @Validated ScoreDto scoreDto,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String token) {
        if (!UserDecoder.isWechatUser(token, WECHAT_USER_PREFIX))
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        WechatUser user = wechatUserService.getUserByOpenid(
                UserDecoder.getWechatUserOpenid(token, WECHAT_USER_PREFIX));
        if (user == null)
            return ResponseBuilder.buildEmpty(FORBIDDEN);

        try {
            if(shopService.scoreShop(scoreDto.getShopId(), user, scoreDto.getScore()))
                return ResponseBuilder.buildEmpty(OK);
            else
                // shouldn't happen at this point
                return ResponseBuilder.buildEmpty(INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            return ResponseBuilder.build(BAD_REQUEST, null, e.getMessage());
        } catch (Exception e) {
            return ResponseBuilder.build(INTERNAL_SERVER_ERROR, null, e.getMessage());
        }
    }
}