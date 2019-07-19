package com.fourseers.parttimejob.arrangement.controller;


import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class WechatCheckController {

    @ApiOperation(value = "Wechat employee check in")
    @PostMapping(value = "/checkin")
    public void checkIn(@ApiParam @RequestBody JSONObject params,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String token ) {

    }
}
