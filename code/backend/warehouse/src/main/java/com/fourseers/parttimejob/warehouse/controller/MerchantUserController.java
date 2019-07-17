package com.fourseers.parttimejob.warehouse.controller;

import com.fourseers.parttimejob.common.util.Response;
import com.fourseers.parttimejob.common.util.ResponseBuilder;
import com.fourseers.parttimejob.warehouse.projection.MerchantUserInfoProjection;
import com.fourseers.parttimejob.warehouse.service.MerchantUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/")
public class MerchantUserController {

    @Autowired
    private MerchantUserService merchantUserService;

    @ApiOperation(value = "Get one  merchant user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "user not exist"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-access-token", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "/merchant-user", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Response<MerchantUserInfoProjection>> getUser(@RequestParam(value = "user_id") Integer userId,
                                                                        @ApiParam(hidden = true) @RequestHeader("x-internal-token") String username) {
        MerchantUserInfoProjection user = merchantUserService.findBriefByUserId(userId);

        if (user == null) {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "user not exist");
        } else {
            return ResponseBuilder.build(HttpStatus.OK, user, "success");
        }
    }
}
