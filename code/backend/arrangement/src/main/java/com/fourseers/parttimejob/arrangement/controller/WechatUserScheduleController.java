package com.fourseers.parttimejob.arrangement.controller;

import com.fourseers.parttimejob.arrangement.dto.ScheduleDto;
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
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/user")
public class WechatUserScheduleController {
    @Value("${app.wechat_user_prefix}")
    private String WECHAT_USER_PREFIX;

    @Autowired
    private JobService jobService;

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private WorkService workService;


    @ApiOperation(value = "Get user's scheduled job within given period of time.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success, returning list of jobs"),
            @ApiResponse(code = 400, message = "Invalid request, could be invalid auth token / start/end time / etc.")
    })
    @GetMapping(value = "schedule")
    public ResponseEntity<Response<List<ScheduleDto>>> getSchedule(
            @RequestParam("begin_date") Date beginDate,
            @RequestParam("end_date") Date endDate,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String token) {
        if(!UserDecoder.isWechatUser(token, WECHAT_USER_PREFIX))
            return ResponseBuilder.buildEmpty(BAD_REQUEST);
        WechatUser user = wechatUserService.getUserByOpenid(
                UserDecoder.getWechatUserOpenid(token, WECHAT_USER_PREFIX));
        if(user == null)
            return ResponseBuilder.buildEmpty(FORBIDDEN);

        if(beginDate.toLocalDate().isAfter(endDate.toLocalDate()))
            return ResponseBuilder.build(BAD_REQUEST, null, "Invalid date.");

        try {
            return ResponseBuilder.build(OK,
                    workService.getSchedule(user, beginDate.toLocalDate(), endDate.toLocalDate()));
        } catch (RuntimeException e) {
            return ResponseBuilder.build(BAD_REQUEST, null, e.getMessage());
        } catch (Exception e) {
            return ResponseBuilder.build(INTERNAL_SERVER_ERROR, null, e.getMessage());
        }
    }
}
