package com.fourseers.parttimejob.warehouse.controller;

import com.fourseers.parttimejob.common.entity.MerchantUser;
import com.fourseers.parttimejob.common.util.Response;
import com.fourseers.parttimejob.common.util.ResponseBuilder;
import com.fourseers.parttimejob.warehouse.projection.MerchantUserInfoProjection;
import com.fourseers.parttimejob.warehouse.service.MerchantUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/")
public class MerchantUserController {

    @Autowired
    private MerchantUserService merchantUserService;

    private final static int PAGE_SIZE = 10;

    @ApiOperation(value = "Get one merchant user")
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

    @ApiOperation(value = "Get merchant users by page")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "You are not admin.")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-access-token", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "/merchant-users", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Response<Page<MerchantUserInfoProjection>>> getUsers(
            @ApiParam(value = "This param tells the server which page to query, starting from 0, with each page having 10 items.")
            @RequestParam(value = "page_count") Integer pageCount,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String username) {
        MerchantUser merchantUser = merchantUserService.findByUsername(username);

        if(merchantUser.getUsername().equals("admin")) {
            Page<MerchantUserInfoProjection> users = merchantUserService.findPageBrief(pageCount, PAGE_SIZE);
            return ResponseBuilder.build(HttpStatus.OK, users, "success");
        } else
            return ResponseBuilder.build(HttpStatus.FORBIDDEN, null, "You are not admin.");
    }

    @ApiOperation(value = "Ban one merchant user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "user not exist"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-access-token", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "/merchant-user/ban", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Response<Void>> banUser(@RequestParam(value = "user_id") Integer userId,
                                                                        @ApiParam(value = "true: ban user, false: unban user") @RequestParam(value = "ban") Boolean ban,
                                                                        @ApiParam(hidden = true) @RequestHeader("x-internal-token") String username) {

        try {
            merchantUserService.ban(userId, ban);
            return ResponseBuilder.build(HttpStatus.OK, null, "success");
        } catch (RuntimeException ex) {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, ex.getMessage());
        }
    }
}
