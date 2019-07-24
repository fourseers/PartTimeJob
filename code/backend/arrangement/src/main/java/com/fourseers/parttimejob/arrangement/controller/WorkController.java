package com.fourseers.parttimejob.arrangement.controller;

import com.fourseers.parttimejob.arrangement.dto.WorkRemarkDto;
import com.fourseers.parttimejob.arrangement.projection.WorkProjection;
import com.fourseers.parttimejob.arrangement.service.WorkService;
import com.fourseers.parttimejob.common.util.Response;
import com.fourseers.parttimejob.common.util.ResponseBuilder;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/merchant")
public class WorkController {

    @Autowired
    private WorkService workService;

    private final static int PAGE_SIZE = 10;

    @ApiOperation(value = "Get one page of work info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "work not exist / user does not belong to a company / incorrect param / shop not exist or not belong to"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-access-token", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "/works", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Response<Page<WorkProjection>>> getWorks(
            @ApiParam(value = "This param is not mandatory. " +
            "If it is present, server will return works from the specified shop, " +
            "otherwise works from all shops belong to the company will be present.")
            @RequestParam(value = "shop_id", required = false) Integer shopId,
            @ApiParam(value = "This param tells the server which page to query, " +
            "starting from 0, with each page having 10 items.")
            @RequestParam(value = "page_count") Integer pageCount,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String username) {
        Page<WorkProjection> workProjections;
        try {
            if (shopId == null) {
                workProjections = workService.findPageByUsername(username, pageCount, PAGE_SIZE);
            } else {
                workProjections = workService.findPageByShopIdAndUsername(shopId, username, pageCount, PAGE_SIZE);
            }
            if (workProjections.isEmpty()) {
                return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "work not exist");
            }
            return ResponseBuilder.build(HttpStatus.OK, workProjections, "success");
        } catch (RuntimeException ex) {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, ex.getMessage());
        }
    }

    @ApiOperation(value = "Rank one work")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "work not exist or not managed by current user / user does not belong to a company / work already marked"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-access-token", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "/work/remark", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Response<Void>> remarkWork(
            @ApiParam(name = "work", value = "Work you wish to remark")
            @RequestBody WorkRemarkDto workRemarkDto,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String username) {

        try {
            workService.remark(username, workRemarkDto.getWorkId(), workRemarkDto.getScore());
            return ResponseBuilder.build(HttpStatus.OK, null, "success");
        } catch (RuntimeException ex) {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, ex.getMessage());
        }
    }
}
