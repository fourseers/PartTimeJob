package com.fourseers.parttimejob.arrangement.controller;

import com.fourseers.parttimejob.arrangement.projection.JobDetailedInfoProjection;
import com.fourseers.parttimejob.arrangement.service.JobService;
import com.fourseers.parttimejob.arrangement.service.WechatUserService;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.common.util.Response;
import com.fourseers.parttimejob.common.util.ResponseBuilder;
import com.fourseers.parttimejob.common.util.UserDecoder;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/user")
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
            @RequestParam(required = false) Float longitude,
            @RequestParam(required = false) Float latitude,
            @RequestHeader(defaultValue = "0") int pageCount,
            @RequestHeader("x-internal-token") String token
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
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        else if(Math.abs(longitude) > 180 || Math.abs(latitude) > 90)
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
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
            @RequestParam @Positive int jobId,
            @RequestHeader("x-internal-token") String token
    ) {
        if(!UserDecoder.isWechatUser(token, WECHAT_USER_PREFIX))
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        WechatUser user = wechatUserService.getUserByOpenid(
                UserDecoder.getWechatUserOpenid(token, WECHAT_USER_PREFIX));
        if(user == null)
            return ResponseBuilder.buildEmpty(FORBIDDEN);

        try {
            return ResponseBuilder.build(OK, jobService.getJobDetail(jobId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.buildEmpty(INTERNAL_SERVER_ERROR);
        }
    }
}
