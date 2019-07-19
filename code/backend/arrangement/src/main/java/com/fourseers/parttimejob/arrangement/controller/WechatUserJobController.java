package com.fourseers.parttimejob.arrangement.controller;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.arrangement.projection.JobDetailedInfoProjection;
import com.fourseers.parttimejob.arrangement.service.JobService;
import com.fourseers.parttimejob.arrangement.service.WechatUserService;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.common.util.Response;
import com.fourseers.parttimejob.common.util.ResponseBuilder;
import com.fourseers.parttimejob.common.util.UserDecoder;
import io.swagger.annotations.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/user")
@Validated
@ApiImplicitParams(
        @ApiImplicitParam(name = "x-internal-token",
                value = "Internal authorization token",
                required = true, dataType = "String", paramType = "header" )
)
public class WechatUserJobController {

    @Value("${app.wechat_user_prefix}")
    private String WECHAT_USER_PREFIX;

    @Autowired
    private JobService jobService;

    @Autowired
    private WechatUserService wechatUserService;

    @ApiOperation(value = "Get paged available jobs for user.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "invalid parameters")
    })
    @GetMapping("/jobs")
    public ResponseEntity<Response<Page<Job>>> getJobList(
            @RequestParam(required = false) @Range(min=-180, max=180)  Float longitude,
            @RequestParam(required = false) @Range(min=-90, max=90) Float latitude,
            @RequestHeader(defaultValue = "0") int pageCount,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String token
    ) {
        if(!UserDecoder.isWechatUser(token, WECHAT_USER_PREFIX))
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        WechatUser user = wechatUserService.getUserByOpenid(
                UserDecoder.getWechatUserOpenid(token, WECHAT_USER_PREFIX));
        if(user == null)
            return ResponseBuilder.buildEmpty(FORBIDDEN);
        if(longitude == null && latitude == null) {
            return ResponseBuilder.build(OK, jobService.findJobs(user, pageCount));
        }
        else if(longitude == null || latitude == null)
            return ResponseBuilder.build(BAD_REQUEST, null,
                    "Longitude and latitude should both appear.");
        else
            return ResponseBuilder.build(OK,
                    jobService.findJobsByGeoLocation(user, longitude, latitude, pageCount));
    }

    @ApiOperation(value = "Get detailed information for one job.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "Invalid job id")
    })
    @GetMapping("/job")
    public ResponseEntity<Response<JobDetailedInfoProjection>> getJobDetail(
            @RequestParam @Min(1) int jobId,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String token
    ) {
        if(!UserDecoder.isWechatUser(token, WECHAT_USER_PREFIX))
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        WechatUser user = wechatUserService.getUserByOpenid(
                UserDecoder.getWechatUserOpenid(token, WECHAT_USER_PREFIX));
        if(user == null)
            return ResponseBuilder.buildEmpty(FORBIDDEN);

        return ResponseBuilder.build(OK, jobService.getJobDetail(jobId));
    }

    @ApiOperation(value = "User apply for job with a set of cv.")
    @PostMapping("apply")
    public ResponseEntity<Response<Void>> applyJob(
            @RequestBody JSONObject params,
            @RequestHeader("x-internal-token") String token
    ) {
        if(!UserDecoder.isWechatUser(token, WECHAT_USER_PREFIX))
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        WechatUser user = wechatUserService.getUserByOpenid(
                UserDecoder.getWechatUserOpenid(token, WECHAT_USER_PREFIX));
        if(user == null)
            return ResponseBuilder.buildEmpty(FORBIDDEN);
        Integer jobId = params.getInteger("jobId");
        String cvId = params.getString("cvId");
        if(jobId == null || cvId == null) {
            return ResponseBuilder.build(BAD_REQUEST, null, "Missing params.");
        }
        try {
            jobService.apply(user, jobId, cvId);
            return ResponseBuilder.buildEmpty(OK);
        } catch (RuntimeException e) {
            return ResponseBuilder.build(BAD_REQUEST, null, e.getMessage());
        } catch (Exception e) {
            return ResponseBuilder.build(INTERNAL_SERVER_ERROR, null, e.getMessage());
        }
    }

}
