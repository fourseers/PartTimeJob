package com.fourseers.parttimejob.arrangement.controller;

import com.fourseers.parttimejob.arrangement.dto.AppliedTimeDto;
import com.fourseers.parttimejob.arrangement.dto.SearchResultDto;
import com.fourseers.parttimejob.arrangement.projection.JobDetailedInfoProjection;
import com.fourseers.parttimejob.arrangement.service.JobService;
import com.fourseers.parttimejob.arrangement.service.WechatUserService;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.common.util.GeoUtil;
import com.fourseers.parttimejob.common.util.Response;
import com.fourseers.parttimejob.common.util.ResponseBuilder;
import com.fourseers.parttimejob.common.util.UserDecoder;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
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

    @ApiOperation(value = "Get paged available jobs for user according to the filter provided.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "invalid parameters")
    })
    @GetMapping("/jobs")
    public ResponseEntity<Response<Page<Job>>> getJobList(
            @RequestParam(required = false) @DecimalMin("-180") @DecimalMax("180") BigDecimal longitude,
            @RequestParam(required = false) @DecimalMin("-90") @DecimalMax("90") BigDecimal latitude,
            @ApiParam("Geo location range in kilometers.")
                @RequestParam(required = false, defaultValue = "30.0") @Positive Double geoRange,
            @ApiParam("How many days before starting to work.")
                @RequestParam(required = false) @Positive Integer daysToCome,
            @ApiParam("Salary range in CNY per day.")
                @RequestParam(required = false) @Positive Double minSalary,
            @ApiParam("Salary range in CNY per day.")
                @RequestParam(required = false) @Positive Double maxSalary,
            @RequestParam(defaultValue = "0") int entryOffset,
            @ApiParam(value = "Tag filtered.")
                @RequestParam(required = false) String tag,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String token
    ) {
        if(!UserDecoder.isWechatUser(token, WECHAT_USER_PREFIX))
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        WechatUser user = wechatUserService.getUserByOpenid(
                UserDecoder.getWechatUserOpenid(token, WECHAT_USER_PREFIX));
        if(user == null)
            return ResponseBuilder.buildEmpty(FORBIDDEN);

        if(longitude == null ^ latitude == null)
            return ResponseBuilder.build(BAD_REQUEST, null, "Longitude and latitude should appear together.");
        else if(minSalary == null ^ maxSalary == null)
            return ResponseBuilder.build(BAD_REQUEST, null, "minSalary and maxSalary should appear together.");


        GeoUtil.Point position = null;
        if(longitude != null)
            position = new GeoUtil.Point(longitude, latitude);
        return ResponseBuilder.build(OK,
                jobService.queryJobs(position, geoRange, daysToCome, minSalary, maxSalary, tag, entryOffset));
    }

    @ApiOperation(value = "Get paged available jobs for user using elastic stack.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "invalid parameters")
    })
    @GetMapping("/jobs-smart")
    public ResponseEntity<Response<SearchResultDto>> getJobListSmart(
            @RequestParam @DecimalMin("-180") @DecimalMax("180") BigDecimal longitude,
            @RequestParam @DecimalMin("-90") @DecimalMax("90") BigDecimal latitude,
            @RequestParam(required = false, defaultValue = "0") Integer entryOffset,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String token) {
        if(!UserDecoder.isWechatUser(token, WECHAT_USER_PREFIX))
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        WechatUser user = wechatUserService.getUserByOpenid(
                UserDecoder.getWechatUserOpenid(token, WECHAT_USER_PREFIX));
        if(user == null)
            return ResponseBuilder.buildEmpty(FORBIDDEN);
        try {
            return ResponseBuilder.build(OK, jobService.findJobsByGeoLocation(user, true, longitude, latitude, entryOffset));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.build(INTERNAL_SERVER_ERROR, null, e.getMessage());
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
