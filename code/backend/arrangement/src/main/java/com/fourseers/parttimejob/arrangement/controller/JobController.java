package com.fourseers.parttimejob.arrangement.controller;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.arrangement.entity.Job;
import com.fourseers.parttimejob.arrangement.entity.Tag;
import com.fourseers.parttimejob.arrangement.service.JobService;
import com.fourseers.parttimejob.arrangement.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/merchant/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addJob(@RequestBody JSONObject body,
                                             @RequestHeader("x-internal-token") String username) {

        int shopId;
        Job job = new Job();
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> getJob(@RequestParam(value = "job_id", required = false) Integer jobId,
                                             @RequestParam(value = "shop_id", required = false) Integer shopId,
                                             @RequestHeader("x-internal-token") String username) {
        JSONObject body = new JSONObject();
        if (shopId == null && jobId == null) {
            try {
                List<Job> jobs = jobService.findByUsername(username);
                body.put("jobs", jobs);
            } catch (RuntimeException ex) {
                return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, ex.getMessage());
            }

        } else if (shopId != null && jobId == null) {
            try {
                List<Job> jobs = jobService.findByShopIdAndUsername(shopId, username);
                body.put("jobs", jobs);
            } catch (RuntimeException ex) {
                return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, ex.getMessage());
            }

        } else if (jobId != null && shopId == null) {
            try {
                Job job = jobService.findByJobIdAndUsername(jobId, username);

                body.put("job", job);
            } catch (RuntimeException ex) {
                return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, ex.getMessage());
            }

        } else {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "incorrect param");
        }
        return ResponseBuilder.build(HttpStatus.OK, body, "success");
    }
}
