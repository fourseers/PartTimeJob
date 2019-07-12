package com.fourseers.parttimejob.warehouse.controller;

import com.fourseers.parttimejob.warehouse.entity.Industry;
import com.fourseers.parttimejob.warehouse.service.IndustryService;
import com.fourseers.parttimejob.warehouse.util.Response;
import com.fourseers.parttimejob.warehouse.util.ResponseBuilder;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/merchant/industry")
public class IndustryController {

    @Autowired
    private IndustryService industryService;

    @ApiOperation(value = "Get all industries")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")
    })
    @RequestMapping(value = "", method = GET, produces = "application/json")
    public ResponseEntity<Response<List<Industry>>> getAllIndustries(@ApiParam(hidden=true) @RequestHeader("x-internal-token") String username) {

        List<Industry> industryList = industryService.findAll();

        return ResponseBuilder.build(HttpStatus.OK, industryList, "success");
    }
}
