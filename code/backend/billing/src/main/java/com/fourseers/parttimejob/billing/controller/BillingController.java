package com.fourseers.parttimejob.billing.controller;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.common.util.Response;
import com.fourseers.parttimejob.common.util.ResponseBuilder;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/merchant")
public class BillingController {
    @Autowired
    private BillingService billingService;

    private final static int PAGE_SIZE = 10;

    @ApiOperation(value = "Get one page of bill info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "bill not exist or not belong to"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-access-token", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<Response<Page<BillingDto>>> getBillings(
            @ApiParam(value = "This param tells the server which page to query, starting from 0, with each page having 10 items.")
            @RequestParam(value = "page_count") Integer pageCount,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String username)
    {

        Page<BillingDto> bills;
        try {
            bills = billingService.findPageByCompanyId(CompanyId, pageCount, PAGE_SIZE);
        } catch (RuntimeException ex) {
            if (ex.getMessage().contains("Page index must not be less than zero!")) {
                return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "incorrect param");
            } else {
                return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, ex.getMessage());
            }
        }

        if ( bills.isEmpty()) {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "no bills");
        } else {
            return ResponseBuilder.build(HttpStatus.OK,  bills, "success");
        }
    }

}
