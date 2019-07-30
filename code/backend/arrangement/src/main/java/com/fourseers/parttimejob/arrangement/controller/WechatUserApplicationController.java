package com.fourseers.parttimejob.arrangement.controller;

import com.fourseers.parttimejob.arrangement.dto.ApplyDto;
import com.fourseers.parttimejob.arrangement.service.JobService;
import com.fourseers.parttimejob.arrangement.service.WechatUserService;
import com.fourseers.parttimejob.arrangement.service.WorkService;
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

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/user")
public class WechatUserApplicationController {

    @Value("${app.wechat_user_prefix}")
    private String WECHAT_USER_PREFIX;

    @Autowired
    private JobService jobService;

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private WorkService workService;

    @ApiOperation(value = "User apply for job with a set of cv.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Application success"),
            @ApiResponse(code = 400, message = "Invalid application, could be invalid parameter / already applied before / already occupied time span." +
                    " See message in response for detail.")
    })
    @PostMapping(value = "apply")
    public ResponseEntity<Response<Void>> applyJob(
            @ApiParam("Param in json, contains jobId and cvId") @RequestBody @Validated ApplyDto params,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String token
    ) {
        if(!UserDecoder.isWechatUser(token, WECHAT_USER_PREFIX))
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        WechatUser user = wechatUserService.getUserByOpenid(
                UserDecoder.getWechatUserOpenid(token, WECHAT_USER_PREFIX));
        if(user == null)
            return ResponseBuilder.buildEmpty(FORBIDDEN);
        if(params.getBeginDate().toLocalDate().isAfter(
                params.getEndDate().toLocalDate()))
            return ResponseBuilder.build(BAD_REQUEST, null, "Invalid applied date.");
        try {
            jobService.apply(user, params);
            return ResponseBuilder.buildEmpty(OK);
        } catch (RuntimeException e) {
            return ResponseBuilder.build(BAD_REQUEST, null, e.getMessage());
        } catch (Exception e) {
            return ResponseBuilder.build(INTERNAL_SERVER_ERROR, null, e.getMessage());
        }
    }


}
