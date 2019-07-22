package com.fourseers.parttimejob.arrangement.controller;

import com.fourseers.parttimejob.arrangement.dto.JobDto;
import com.fourseers.parttimejob.arrangement.service.JobService;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.util.Response;
import com.fourseers.parttimejob.common.util.ResponseBuilder;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/merchant")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private ModelMapper modelMapper;

    private final static int PAGE_SIZE = 10;

    @ApiOperation(value = "Create one job")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "time incorrect / incorrect param / shop not exist or not belong to"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-access-token", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "/job", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Response<Void>> addJob(@ApiParam(name = "data", value = "info about job")@RequestBody(required = true) JobDto jobDto,
                                                 @ApiParam(hidden=true) @RequestHeader("x-internal-token") String username) {

        Job job;
        try {
            job = modelMapper.map(jobDto, Job.class);
            job.setJobId(null);

            if (jobDto.getBeginDate().after(jobDto.getEndDate()) ||
                    job.getBeginApplyTime().after(job.getEndApplyTime()) ||
                    job.getEndApplyTime().after(job.getEndDate())) {
                throw new RuntimeException("time incorrect");
            }
            if (jobDto.getSalary() < 0) {
                throw new RuntimeException("incorrect param");
            }
        } catch (RuntimeException ex) {
            if (ex.getMessage().equals("time incorrect")) {
                return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, ex.getMessage());
            } else if (ex.getMessage().equals("incorrect param")) {
                return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, ex.getMessage());
            }
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "unhandled exception");
        }

        try {
            jobService.save(job, job.getShop().getShopId(), username);
        } catch (RuntimeException ex) {
            if (ex.getMessage().contains("TAG")) {
                return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "incorrect tag");
            }
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "shop not exist or not belong to");
        }

        return ResponseBuilder.build(HttpStatus.OK, null, "success");
    }

    @ApiOperation(value = "Get one job info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "user does not belong to a company / job not exist or not belong to"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-access-token", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "/job", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Response<Job>> getJob(@RequestParam(value = "job_id") Integer jobId,
                                                @ApiParam(hidden = true) @RequestHeader("x-internal-token") String username) {
        try {
            Job job = jobService.findByJobIdAndUsername(jobId, username);
            return ResponseBuilder.build(HttpStatus.OK, job, "success");
        } catch (RuntimeException ex) {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, ex.getMessage());
        }
    }


    @ApiOperation(value = "Get one page of job info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "job not exist / user does not belong to a company / shop not exist or not belong to / user does not belong to a company"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-access-token", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "/jobs", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Response<Page<Job>>> getJobs(
            @ApiParam(value = "This param is not mandatory. " +
            "If it is present, server will return jobs from the specified shop, " +
            "otherwise jobs from all shops belong to the company will be present.")
            @RequestParam(value = "shop_id", required = false) Integer shopId,
            @ApiParam(value = "This param tells the server which page to query, " +
            "starting from 0, with each page having 10 items.")
            @RequestParam(value = "page_count") Integer pageCount,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String username) {

        Page<Job> jobs;
        try {
            if (shopId == null) {
                jobs = jobService.findPageByUsername(username, pageCount, PAGE_SIZE);
            } else {
                jobs = jobService.findPageByShopIdAndUsername(shopId, username, pageCount, PAGE_SIZE);
            }
            if (jobs.isEmpty()) {
                return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "job not exist");
            }
            return ResponseBuilder.build(HttpStatus.OK, jobs, "success");
        } catch (RuntimeException ex) {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, ex.getMessage());
        }
    }

    @ApiOperation(value = "Manually stop job hiring")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "user does not belong to a company / job not exist or not belong to"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-access-token", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "/job/stop", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Response<Void>> stopJob(@RequestParam(value = "job_id") Integer jobId,
                                                @ApiParam(value = "false: restart job hiring, true: stop job hiring") @RequestParam(value = "stop") Boolean stop,
                                                @ApiParam(hidden = true) @RequestHeader("x-internal-token") String username) {
        try {
            jobService.setJobHiringState(jobId, username, stop);
            return ResponseBuilder.build(HttpStatus.OK, null, "success");
        } catch (RuntimeException ex) {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, ex.getMessage());
        }
    }
}
