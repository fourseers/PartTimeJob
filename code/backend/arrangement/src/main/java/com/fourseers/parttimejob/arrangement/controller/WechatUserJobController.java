package com.fourseers.parttimejob.arrangement.controller;

import com.fourseers.parttimejob.arrangement.dto.AppliedTimeDto;
import com.fourseers.parttimejob.arrangement.dto.SearchResultDto;
import com.fourseers.parttimejob.arrangement.projection.JobDetailedInfoProjection;
import com.fourseers.parttimejob.arrangement.service.JobService;
import com.fourseers.parttimejob.arrangement.service.WechatUserService;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.common.util.Response;
import com.fourseers.parttimejob.common.util.ResponseBuilder;
import com.fourseers.parttimejob.common.util.UserDecoder;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/user")
@Validated
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
    public ResponseEntity<Response<SearchResultDto>> getJobList(
            @RequestParam(required = false) @DecimalMin("-180") @DecimalMax("180") BigDecimal longitude,
            @RequestParam(required = false) @DecimalMin("-90") @DecimalMax("90") BigDecimal latitude,
            @RequestParam(defaultValue = "0") int entryOffset,
            @ApiParam(value = "determine whether take user tags into consideration or not")
                @RequestParam(required = false, defaultValue = "true") Boolean useTag,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String token
    ) {
        if(!UserDecoder.isWechatUser(token, WECHAT_USER_PREFIX))
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        WechatUser user = wechatUserService.getUserByOpenid(
                UserDecoder.getWechatUserOpenid(token, WECHAT_USER_PREFIX));
        if(user == null)
            return ResponseBuilder.buildEmpty(FORBIDDEN);
        if(longitude == null && latitude == null) {
            try {
                return ResponseBuilder.build(OK, jobService.findJobs(user, useTag, entryOffset));
            } catch (RuntimeException e) {
                return ResponseBuilder.build(BAD_REQUEST, null, e.getMessage());
            } catch (Exception e) {
                return ResponseBuilder.build(INTERNAL_SERVER_ERROR, null, e.getMessage());
            }
        }
        else if(longitude == null || latitude == null)
            return ResponseBuilder.build(BAD_REQUEST, null,
                    "Longitude and latitude should both appear.");
        else {
            try {
                return ResponseBuilder.build(OK,
                        jobService.findJobsByGeoLocation(user, useTag, longitude, latitude, entryOffset));
            } catch (RuntimeException e) {
                return ResponseBuilder.build(BAD_REQUEST, null, e.getMessage());
            } catch (Exception e) {
                return ResponseBuilder.build(INTERNAL_SERVER_ERROR, null, e.getMessage());
            }
        }
    }

    @ApiOperation(value = "Get detailed information for one job.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "Invalid job id")
    })
    @GetMapping("/job")
    public ResponseEntity<Response<JobDetailedInfoProjection>> getJobDetail(
            @RequestParam("job_id") @Min(1) int jobId,
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

    @ApiOperation(value = "Get applied dates for one job.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "invalid job id")
    })
    @GetMapping("/job/applied-time")
    public ResponseEntity<Response<AppliedTimeDto>> getAppliedDates(
            @ApiParam @RequestParam("job_id") Integer jobId,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String token) {
        if(!UserDecoder.isWechatUser(token, WECHAT_USER_PREFIX))
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        WechatUser user = wechatUserService.getUserByOpenid(
                UserDecoder.getWechatUserOpenid(token, WECHAT_USER_PREFIX));
        if(user == null)
            return ResponseBuilder.buildEmpty(FORBIDDEN);
        try {
            return ResponseBuilder.build(OK, jobService.getJobAppliedTime(jobId));
        } catch (RuntimeException e) {
            return ResponseBuilder.build(BAD_REQUEST, null, e.getMessage());
        } catch (Exception e) {
            return ResponseBuilder.build(INTERNAL_SERVER_ERROR, null, e.getMessage());
        }
    }



}
