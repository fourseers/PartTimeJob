package com.fourseers.parttimejob.arrangement.controller;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.arrangement.repository.JobRepository;
import com.fourseers.parttimejob.arrangement.repository.ShopRepository;
import com.fourseers.parttimejob.common.entity.Etc;
import com.fourseers.parttimejob.common.entity.Job;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class WechatCheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Value("${app.wechat_user_prefix}")
    private String WECHAT_USER_PREFIX;

    private String userHeader;
    private String invalidUserHeader;

    private Integer jobId;
    private Integer invalidCheckInJobId;
    private Integer invalidCheckOutJobId;

    @Before
    public void before() throws Exception {
        userHeader = WECHAT_USER_PREFIX + "fakeOpenid";
        invalidUserHeader = WECHAT_USER_PREFIX + "wrongOpenid";
        Timestamp yesterdayLastWeek = Timestamp.valueOf(LocalDateTime.now().minusDays(1).minusWeeks(1));
        Timestamp tomorrowLastWeek = Timestamp.valueOf(LocalDateTime.now().plusDays(1).minusWeeks(1));
        Timestamp yesterday = Timestamp.valueOf(LocalDateTime.now().minusDays(1));
        Timestamp tomorrow = Timestamp.valueOf(LocalDateTime.now().plusDays(1));
        Timestamp yesterdayNextWeek = Timestamp.valueOf(LocalDateTime.now().minusDays(1).plusWeeks(1));
        Timestamp tomorrowNextWeek = Timestamp.valueOf(LocalDateTime.now().plusDays(1).plusWeeks(1));

        Job job = new Job();
        job.setNeedAmount(50);
        job.setJobDetail("job detail...");
        job.setBeginApplyTime(yesterday);
        job.setEndApplyTime(tomorrow);
        job.setBeginDate(new Date(yesterdayNextWeek.getTime()));
        job.setEndDate(new Date(tomorrowNextWeek.getTime()));
        job.setSalary(100.0);
        job.setEducation(Etc.Education.SENIOR_HIGH);
        job.setShop(shopRepository.getOne(1));
        job.setJobName("Teach abc");
        job.setNeedGender(2);

        job.setBeginTime(Time.valueOf(LocalTime.now().minusMinutes(45)));
        job.setEndTime(Time.valueOf(LocalTime.now().plusMinutes(10)));
        jobRepository.save(job);
        invalidCheckInJobId = job.getJobId();

        job = new Job();
        job.setNeedAmount(50);
        job.setJobDetail("job detail...");
        job.setBeginApplyTime(yesterday);
        job.setEndApplyTime(tomorrow);
        job.setBeginDate(new Date(yesterdayNextWeek.getTime()));
        job.setEndDate(new Date(tomorrowNextWeek.getTime()));
        job.setSalary(100.0);
        job.setEducation(Etc.Education.SENIOR_HIGH);
        job.setShop(shopRepository.getOne(1));
        job.setJobName("Teach abc");
        job.setNeedGender(2);

        job.setBeginTime(Time.valueOf(LocalTime.now().minusMinutes(10)));
        job.setEndTime(Time.valueOf(LocalTime.now().plusMinutes(10)));
        jobRepository.save(job);
        jobId = job.getJobId();

        job = new Job();
        job.setNeedAmount(50);
        job.setJobDetail("job detail...");
        job.setBeginApplyTime(yesterday);
        job.setEndApplyTime(tomorrow);
        job.setBeginDate(new Date(yesterdayNextWeek.getTime()));
        job.setEndDate(new Date(tomorrowNextWeek.getTime()));
        job.setSalary(100.0);
        job.setEducation(Etc.Education.SENIOR_HIGH);
        job.setShop(shopRepository.getOne(1));
        job.setJobName("Teach abc");
        job.setNeedGender(2);

        job.setBeginTime(Time.valueOf(LocalTime.now().minusMinutes(10)));
        job.setEndTime(Time.valueOf(LocalTime.now().plusMinutes(45)));
        jobRepository.save(job);
        invalidCheckOutJobId = job.getJobId();
    }

    @After
    public void after() throws Exception {
    }

    /**
     *
     * Method: checkIn(@ApiParam @RequestBody CheckinDto params, @ApiParam(hidden = true) @RequestHeader("x-internal-token") String token)
     *
     */
    @Test
    public void testCheckInSuccess() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("job_id", jobId)
                .fluentPut("longitude", 120)
                .fluentPut("latitude", 30);
        mockMvc.perform(post("/user/checkin")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCheckInIncompleteLocation() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("job_id", invalidCheckInJobId)
                .fluentPut("longitude", 120);
        mockMvc.perform(post("/user/checkin")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testCheckInInvalidLocation() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("job_id", invalidCheckInJobId)
                .fluentPut("latitude", 300)
                .fluentPut("longitude", 120);
        MvcResult result = mockMvc.perform(post("/user/checkin")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
        result.getResponse().getContentAsString();
    }

    @Test
    public void testCheckInImproperTime() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("job_id", invalidCheckInJobId)
                .fluentPut("longitude", 120)
                .fluentPut("latitude", 30);
        mockMvc.perform(post("/user/checkin")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void testCheckInImproperLocation() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("job_id", jobId)
                .fluentPut("longitude", 120.1)
                .fluentPut("latitude", 30.1); // 14 km away
        mockMvc.perform(post("/user/checkin")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testCheckInAlreadyChecked() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("job_id", jobId)
                .fluentPut("longitude", 120)
                .fluentPut("latitude", 30);
        mockMvc.perform(post("/user/checkin")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // do it again
        mockMvc.perform(post("/user/checkin")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testCheckOutSuccess() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("job_id", jobId)
                .fluentPut("longitude", 120)
                .fluentPut("latitude", 30);
        mockMvc.perform(post("/user/checkin")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/user/checkout")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCheckOutNotCheckedInYet() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("job_id", jobId)
                .fluentPut("longitude", 120)
                .fluentPut("latitude", 30);

        mockMvc.perform(post("/user/checkout")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testCheckOutImproperLocation() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("job_id", jobId)
                .fluentPut("longitude", 120)
                .fluentPut("latitude", 30);
        mockMvc.perform(post("/user/checkin")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        req.put("longitude", 120.1);
        req.put("latitude", 30.1); // 14km away
        mockMvc.perform(post("/user/checkout")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void testCheckOutImproperTime() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("job_id", invalidCheckOutJobId)
                .fluentPut("longitude", 120)
                .fluentPut("latitude", 30);
        mockMvc.perform(post("/user/checkin")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        req.put("longitude", 120);
        req.put("latitude", 30); // 14km away
        mockMvc.perform(post("/user/checkout")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void testCheckOutAlreadyCheckOut() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("job_id", jobId)
                .fluentPut("longitude", 120)
                .fluentPut("latitude", 30);
        mockMvc.perform(post("/user/checkin")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(post("/user/checkout")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(post("/user/checkout")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
} 
