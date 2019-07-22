package com.fourseers.parttimejob.billing.controller;

import com.fourseers.parttimejob.billing.dto.WorkBillingDto;
import com.fourseers.parttimejob.billing.projection.WorkBillingProjection;
import com.fourseers.parttimejob.billing.service.BillingService;
import com.fourseers.parttimejob.common.entity.Billing;
import com.fourseers.parttimejob.common.util.Response;
import com.fourseers.parttimejob.common.util.ResponseBuilder;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/merchant/billing")
public class BillingController {

    @Autowired
    private BillingService billingService;

    private final static int PAGE_SIZE = 10;

    @ApiOperation(value = "Get one page of bill info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "incorrect param / no bills / user does not belong to any company"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-access-token", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "", method = GET, produces = "application/json")
    public ResponseEntity<Response<Page<WorkBillingProjection>>> getBillings(
            @ApiParam(value = "This param tells the server which page to query, starting from 0, with each page having 10 items.")
            @RequestParam(value = "page_count") Integer pageCount,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String username) {

        Page<WorkBillingProjection> bills;
        try {
            bills = billingService.getBillingsByUsernameOrderByBillIdDesc(username, pageCount, PAGE_SIZE);
        } catch (RuntimeException ex) {
            if (ex.getMessage().contains("Page index must not be less than zero!")) {
                return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "incorrect param");
            } else {
                return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, ex.getMessage());
            }
        }

        if ( bills.isEmpty()) {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "no bills");
        }

        return ResponseBuilder.build(HttpStatus.OK, bills, "success");
    }

    @ApiOperation(value = "Pay one billing")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "user does not belong to any company / work not exist or not belong to current company / work already paid"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-access-token", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "/pay", method = POST, produces = "application/json")
    public ResponseEntity<Response<Void>> payBilling(
            @ApiParam(value = "Work you wish to pay")
            @RequestBody WorkBillingDto workBillingDto,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String username) {

       try {
           Billing billing = new Billing();
           billing.setPayment(workBillingDto.getPayment());
           billing.setMethod(workBillingDto.getMethod());
           billing.setMeta(workBillingDto.getMeta());
           billingService.payBill(username, workBillingDto.getWorkId(), billing);
           return ResponseBuilder.build(HttpStatus.OK, null, "success");
       } catch (RuntimeException ex) {
           return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, ex.getMessage());
       }
    }

    @ApiOperation(value = "Get billing sum in a given period")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = ""),
    })
            @ApiImplicitParams({
                    @ApiImplicitParam(name = "x-access-token", value = "Authorization token",
                            required = true, dataType = "string", paramType = "header")
            })
    @RequestMapping(value = "/sum", method = GET, produces = "application/json")
    public ResponseEntity<Response<Void>> getBillings(
            @ApiParam(value = "This param tells the server which page to query, starting from 0, with each page having 10 items.")
            @RequestParam(value = "from") long from,
            @RequestParam(value = "to") long to,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String username) {
        Timestamp fromTS = new Timestamp(from);
        Timestamp toTS = new Timestamp(to);
        return null;
    }
}
