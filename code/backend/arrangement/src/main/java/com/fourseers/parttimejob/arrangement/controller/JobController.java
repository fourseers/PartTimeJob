package com.fourseers.parttimejob.arrangement.controller;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.arrangement.service.JobService;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.Tag;
import com.fourseers.parttimejob.common.util.Response;
import com.fourseers.parttimejob.common.util.ResponseBuilder;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping(value = "/merchant")
public class JobController {

    @Autowired
    private JobService jobService;

    private final static int PAGE_SIZE = 10;

    @RequestMapping(value = "/job", method = RequestMethod.POST)
    public ResponseEntity<Response<JSONObject>> addJob(@RequestBody JSONObject body,
                                                       @RequestHeader("x-internal-token") String username) {

        int shopId;
        Job job = new Job();
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        try {
            shopId = body.getIntValue("shop_id");
            job.setJobName(body.getString("job_name"));
            Date beginDate = format.parse(body.getString("begin_date"));
            job.setBeginDate(new Timestamp(beginDate.getTime()));
            Date endDate = format.parse(body.getString("end_date"));
            job.setEndDate(new Timestamp(endDate.getTime()));
            job.setJobDetail(body.getString("job_detail"));
            job.setNeedGender(body.getInteger("need_gender"));
            job.setNeedAmount(body.getInteger("need_amount"));
            Date beginApplyDate = format.parse(body.getString("begin_apply_date"));
            job.setBeginApplyDate(new Timestamp(beginApplyDate.getTime()));
            Date endApplyDate = format.parse(body.getString("end_apply_date"));
            job.setEndApplyDate(new Timestamp(endApplyDate.getTime()));
            job.setEducation(body.getString("education"));
            List<Tag> tagList = new ArrayList<>();
            for (int i = 0; i < body.getJSONArray("tag_list").size(); i++) {
                Tag tag = new Tag();
                tag.setId((Integer) body.getJSONArray("tag_list").get(i));
                tagList.add(tag);
            }
            job.setTagList(tagList);
            job.setSalary(body.getDouble("salary"));

            if (beginDate.after(endDate) || beginApplyDate.after(endApplyDate) || endApplyDate.after(beginDate)) {
                throw new RuntimeException("time incorrect");
            }
        } catch (ParseException ex) {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "incorrect param");
        } catch (RuntimeException ex) {
            if (ex.getMessage().equals("time incorrect")) {
                return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, ex.getMessage());
            }
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "unhandled exception");
        }

        try {
            jobService.save(job, shopId, username);
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
}
