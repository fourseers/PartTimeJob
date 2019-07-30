package com.fourseers.parttimejob.arrangement.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.arrangement.repository.*;
import com.fourseers.parttimejob.arrangement.service.JobService;
import com.fourseers.parttimejob.common.entity.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class WechatUserApplicationControllerTest {

    @Autowired
    WechatUserJobController wechatUserJobController;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CVRepository cvRepository;

    @Autowired
    private WechatUserRepository wechatUserRepository;

    @Autowired
    private JobService jobService;

    private String userHeader, user2Header;
    private String invalidUserHeader;
    private String userCVId, user2CVId;
    private Integer goodJobId, goodJob2Id, noEduJobId, outdateJobId, fullJobId, femaleJobId;
    private Integer stoppedJobId;

    @Value("${app.wechat_user_prefix}")
    private String WECHAT_USER_PREFIX;

    @Before
    public void before() throws Exception {

        userHeader = WECHAT_USER_PREFIX + "fakeOpenid";
        user2Header = WECHAT_USER_PREFIX + "fakeOpenid2";
        invalidUserHeader = WECHAT_USER_PREFIX + "wrongOpenid";

        WechatUser user = wechatUserRepository.getOne(1),
                user2 = wechatUserRepository.getOne(2);
        CV cv = new CV();
        cv.setUserId(user.getUserId());
        cv.setName(user.getName());
        cv.setGender(user.getGender());
        cv.setTitle("hahaha");
        cv.setHeight(180f);
        cv.setWeight(80f);
        cv.setExperiences(Arrays.asList("exp 1", "exp 2"));
        cv.setPhone("12312341234");
        cv.setIdentity("310110123412341234");
        cv.setEducation(Etc.Education.SENIOR_HIGH);
        cv.setStatement("default content goes here...");
        cvRepository.save(cv);
        userCVId = cv.getId();

        cv = new CV();
        cv.setUserId(user2.getUserId());
        cv.setName(user.getName());
        cv.setGender(user.getGender());
        cv.setTitle("hahaha");
        cv.setHeight(180f);
        cv.setWeight(80f);
        cv.setExperiences(Arrays.asList("exp 1", "exp 2"));
        cv.setPhone("12312345678");
        cv.setIdentity("310110123412348765");
        cv.setEducation(Etc.Education.SENIOR_HIGH);
        cv.setStatement("default content goes here...");
        cvRepository.save(cv);
        user2CVId = cv.getId();

        Calendar yesterdayCalendar = Calendar.getInstance();
        Calendar tomorrowCalendar = Calendar.getInstance();
        yesterdayCalendar.add(Calendar.DATE, -1);
        tomorrowCalendar.add(Calendar.DATE, 1);

        yesterdayCalendar.add(Calendar.WEEK_OF_YEAR, -1);
        tomorrowCalendar.add(Calendar.WEEK_OF_YEAR, -1);
        Timestamp yesterdayLastWeek = new Timestamp(yesterdayCalendar.getTime().getTime());
        Timestamp tomorrowLastWeek = new Timestamp(tomorrowCalendar.getTime().getTime());
        yesterdayCalendar.add(Calendar.WEEK_OF_YEAR, 1);
        tomorrowCalendar.add(Calendar.WEEK_OF_YEAR, 1);
        Timestamp yesterday = new Timestamp(yesterdayCalendar.getTime().getTime());
        Timestamp tomorrow = new Timestamp(tomorrowCalendar.getTime().getTime());
        yesterdayCalendar.add(Calendar.WEEK_OF_YEAR, 1);
        tomorrowCalendar.add(Calendar.WEEK_OF_YEAR, 1);
        Timestamp yesterdayNextWeek = new Timestamp(yesterdayCalendar.getTime().getTime());
        Timestamp tomorrowNextWeek = new Timestamp(tomorrowCalendar.getTime().getTime());

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
        job.setBeginTime(Time.valueOf(LocalTime.of(6,0)));
        job.setEndTime(Time.valueOf(LocalTime.of(18,0)));
        job.setNeedGender(2);
        jobRepository.save(job);
        goodJobId = job.getJobId();

        job = new Job();
        job.setNeedAmount(50);
        job.setJobDetail("job detail...");
        job.setBeginApplyTime(yesterday);
        job.setEndApplyTime(tomorrow);
        job.setBeginDate(new Date(yesterdayNextWeek.getTime()));
        job.setEndDate(new Date(tomorrowNextWeek.getTime()));
        job.setSalary(100.0);
        job.setEducation(Etc.Education.SENIOR_HIGH);
        job.setShop(shopRepository.getOne(2));
        job.setJobName("Teach abc");
        job.setBeginTime(Time.valueOf(LocalTime.of(6,0)));
        job.setEndTime(Time.valueOf(LocalTime.of(18,0)));
        job.setNeedGender(2);
        jobRepository.save(job);
        goodJob2Id = job.getJobId();

        job = new Job();
        job.setNeedAmount(50);
        job.setJobDetail("job detail...");
        job.setBeginApplyTime(yesterday);
        job.setEndApplyTime(tomorrow);
        job.setBeginDate(new Date(yesterdayNextWeek.getTime()));
        job.setEndDate(new Date(tomorrowNextWeek.getTime()));job.setSalary(100.0);
        job.setEducation(Etc.Education.BACHELOR);
        job.setShop(shopRepository.getOne(1));
        job.setJobName("Teach abc");
        job.setBeginTime(Time.valueOf(LocalTime.of(6,0)));
        job.setEndTime(Time.valueOf(LocalTime.of(18,0)));
        job.setNeedGender(2);
        jobRepository.save(job);
        noEduJobId = job.getJobId();

        job = new Job();
        job.setNeedAmount(50);
        job.setJobDetail("job detail...");
        job.setBeginApplyTime(yesterdayLastWeek);
        job.setEndApplyTime(tomorrowLastWeek);
        job.setBeginDate(new Date(yesterdayNextWeek.getTime()));
        job.setEndDate(new Date(tomorrowNextWeek.getTime()));job.setSalary(100.0);
        job.setEducation(Etc.Education.SENIOR_HIGH);
        job.setShop(shopRepository.getOne(1));
        job.setJobName("Teach abc");
        job.setBeginTime(Time.valueOf(LocalTime.of(6,0)));
        job.setEndTime(Time.valueOf(LocalTime.of(18,0)));
        job.setNeedGender(2);
        jobRepository.save(job);
        outdateJobId = job.getJobId();

        job = new Job();
        job.setNeedAmount(50);
        job.setAppliedAmount(50);
        job.setJobDetail("job detail...");
        job.setBeginApplyTime(yesterday);
        job.setEndApplyTime(tomorrow);
        job.setBeginDate(new Date(yesterdayNextWeek.getTime()));
        job.setEndDate(new Date(tomorrowNextWeek.getTime()));job.setSalary(100.0);
        job.setEducation(Etc.Education.SENIOR_HIGH);
        job.setShop(shopRepository.getOne(1));
        job.setJobName("Teach abc");
        job.setBeginTime(Time.valueOf(LocalTime.of(6,0)));
        job.setEndTime(Time.valueOf(LocalTime.of(18,0)));
        job.setNeedGender(2);
        jobRepository.save(job);
        fullJobId = job.getJobId();

        job = new Job();
        job.setNeedAmount(50);
        job.setJobDetail("job detail...");
        job.setBeginApplyTime(yesterday);
        job.setEndApplyTime(tomorrow);
        job.setBeginDate(new Date(yesterdayNextWeek.getTime()));
        job.setEndDate(new Date(tomorrowNextWeek.getTime()));job.setSalary(100.0);
        job.setEducation(Etc.Education.SENIOR_HIGH);
        job.setShop(shopRepository.getOne(1));
        job.setJobName("Teach English");
        job.setBeginTime(Time.valueOf(LocalTime.of(6,0)));
        job.setEndTime(Time.valueOf(LocalTime.of(18,0)));
        job.setNeedGender(0);   // require female
        jobRepository.save(job);
        femaleJobId = job.getJobId();

        job = new Job();
        job.setManualStop(true);
        job.setNeedAmount(50);
        job.setJobDetail("job detail...");
        job.setBeginApplyTime(yesterday);
        job.setEndApplyTime(tomorrow);
        job.setBeginDate(new Date(yesterdayNextWeek.getTime()));
        job.setEndDate(new Date(tomorrowNextWeek.getTime()));job.setSalary(100.0);
        job.setEducation(Etc.Education.SENIOR_HIGH);
        job.setShop(shopRepository.getOne(1));
        job.setJobName("Teach English");
        job.setBeginTime(Time.valueOf(LocalTime.of(6,0)));
        job.setEndTime(Time.valueOf(LocalTime.of(18,0)));
        job.setNeedGender(2);
        jobRepository.save(job);
        stoppedJobId = job.getJobId();
    }

    @After
    public void after() throws Exception {
        applicationRepository.deleteAll();
        jobRepository.deleteAll();
        cvRepository.deleteAll();
    }

// user job applications

    @Test
    public void testUserApplyJobSuccess() throws Exception {
        Job job = jobRepository.getOne(goodJobId);
        JSONObject req = new JSONObject()
                .fluentPut("job_id", goodJobId)
                .fluentPut("cv_id", userCVId)
                .fluentPut("begin_date", job.getBeginDate().toString())
                .fluentPut("end_date", job.getEndDate().toString());
        mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().isOk());
    }


    @Test
    public void testUserApplyJobNoJobId() throws Exception {
        Job job = jobRepository.getOne(goodJobId);
        JSONObject req = new JSONObject()
                .fluentPut("cv_id", userCVId)
                .fluentPut("begin_date", job.getBeginDate().toString())
                .fluentPut("end_date", job.getEndDate().toString());
        mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUserApplyJobNoCV() throws Exception {
        Job job = jobRepository.getOne(goodJobId);
        JSONObject req = new JSONObject()
                .fluentPut("job_id", goodJobId)
                .fluentPut("begin_date", job.getBeginDate().toString())
                .fluentPut("end_date", job.getEndDate().toString());;
        mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUserApplyJobInvalidDates() throws Exception {
        // end date is before begin date
        Job job = jobRepository.getOne(outdateJobId);
        JSONObject req = new JSONObject()
                .fluentPut("job_id", outdateJobId)
                .fluentPut("cv_id", userCVId)
                .fluentPut("begin_date", job.getEndDate().toString())
                .fluentPut("end_date", job.getBeginDate().toString());
        mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUserApplyJobMissedApplicationDate() throws Exception {
        Job job = jobRepository.getOne(outdateJobId);
        JSONObject req = new JSONObject()
                .fluentPut("job_id", outdateJobId)
                .fluentPut("cv_id", userCVId)
                .fluentPut("begin_date", job.getBeginDate().toString())
                .fluentPut("end_date", job.getEndDate().toString());
        mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUserApplyJobNoEdu() throws Exception {
        Job job = jobRepository.getOne(noEduJobId);
        JSONObject req = new JSONObject()
                .fluentPut("job_id", noEduJobId)
                .fluentPut("cv_id", userCVId).fluentPut("begin_date", job.getBeginDate().toString())
                .fluentPut("end_date", job.getEndDate().toString());;
        mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testUserApplyJobInvalidCV() throws Exception {
        Job job = jobRepository.getOne(goodJobId);
        JSONObject req = new JSONObject()
                .fluentPut("job_id", goodJobId)
                .fluentPut("cv_id", "abcdef")
                .fluentPut("begin_date", job.getBeginDate().toString())
                .fluentPut("end_date", job.getEndDate().toString());
        mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUserApplyJobInvalidJob() throws Exception {
        Job job = jobRepository.getOne(goodJobId);
        JSONObject req = new JSONObject()
                .fluentPut("job_id", 1000)
                .fluentPut("cv_id", userCVId)
                .fluentPut("begin_date", job.getBeginDate().toString())
                .fluentPut("end_date", job.getEndDate().toString());
        mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUserApplyJobNoMoreSeats() throws Exception {
        Job job = jobRepository.getOne(fullJobId);
        JSONObject req = new JSONObject()
                .fluentPut("job_id", fullJobId)
                .fluentPut("cv_id", userCVId).fluentPut("begin_date", job.getBeginDate().toString())
                .fluentPut("end_date", job.getEndDate().toString());
        mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUserApplyJobDifferentGender() throws Exception {
        Job job = jobRepository.getOne(femaleJobId);
        JSONObject req = new JSONObject()
                .fluentPut("job_id", femaleJobId)
                .fluentPut("cv_id", userCVId)
                .fluentPut("begin_date", job.getBeginDate().toString())
                .fluentPut("end_date", job.getEndDate().toString());
        mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUserApplyJobManuallyStopped() throws Exception {
        Job job = jobRepository.getOne(stoppedJobId);
        JSONObject req = new JSONObject()
                .fluentPut("job_id", stoppedJobId)
                .fluentPut("cv_id", userCVId)
                .fluentPut("begin_date", job.getBeginDate().toString())
                .fluentPut("end_date", job.getEndDate().toString());
        mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUserApplyJobForGivenPeriodOfTime() throws Exception {
        Job job = jobRepository.findByJobId(goodJobId);
        JSONObject req = new JSONObject()
                .fluentPut("job_id", goodJobId)
                .fluentPut("cv_id", userCVId)
                .fluentPut("begin_date", job.getBeginDate().toString())
                .fluentPut("end_date",
                        Date.valueOf(job.getBeginDate().toLocalDate().plusDays(1)).toString());
        mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().isOk());
    }

    @Test
    public void testUserApplyJobMultipleApps() throws Exception {
        Job job = jobRepository.findByJobId(goodJobId);
        JSONObject req = new JSONObject()
                .fluentPut("job_id", goodJobId)
                .fluentPut("cv_id", userCVId)
                .fluentPut("begin_date", job.getBeginDate().toString())
                .fluentPut("end_date", job.getEndDate().toString());
        mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().isOk());
        MvcResult result = mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().isBadRequest())
                .andReturn();
        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals(response.getString("message"), "You've already applied for that job.");
    }


    @Test
    public void testUserApplyJobTimePeriodOccupied() throws Exception {
        Job job = jobRepository.findByJobId(goodJobId);
        JSONObject req = new JSONObject()
                .fluentPut("job_id", goodJobId)
                .fluentPut("cv_id", userCVId)
                .fluentPut("begin_date", job.getBeginDate().toString())
                .fluentPut("end_date",
                        Date.valueOf(job.getBeginDate().toLocalDate().plusDays(1)).toString());
        mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().isOk());
        // mock that merchant user approved application
        WechatUser user = wechatUserRepository.getOne(1);
        Application application = applicationRepository.findByWechatUserAndJob(user, job);
        application.setStatus(true);
        applicationRepository.save(application);
        // another user applied
        req = new JSONObject()
                .fluentPut("job_id", goodJobId)
                .fluentPut("cv_id", user2CVId)
                .fluentPut("begin_date", job.getBeginDate().toString())
                .fluentPut("end_date",
                        Date.valueOf(job.getBeginDate().toLocalDate().plusDays(1)).toString());
        MvcResult result =  mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", user2Header))
                .andExpect(status().isBadRequest())
                .andReturn();
        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("Sorry, time already applied.", response.getString("message"));
    }


    @Test
    public void testMultipleUserApplyForOneJob() throws Exception {
        Job job = jobRepository.findByJobId(goodJobId);
        JSONObject req = new JSONObject()
                .fluentPut("job_id", goodJobId)
                .fluentPut("cv_id", userCVId)
                .fluentPut("begin_date", job.getBeginDate().toString())
                .fluentPut("end_date",
                        Date.valueOf(job.getBeginDate().toLocalDate().plusDays(1)).toString());
        mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().isOk());
        // mock that merchant user approved application
        WechatUser user = wechatUserRepository.getOne(1);
        Application application = applicationRepository.findByWechatUserAndJob(user, job);
        application.setStatus(true);
        applicationRepository.save(application);
        // another user applied
        req = new JSONObject()
                .fluentPut("job_id", goodJobId)
                .fluentPut("cv_id", user2CVId)
                .fluentPut("begin_date", job.getEndDate())
                .fluentPut("end_date", job.getEndDate());
        mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", user2Header))
                .andExpect(status().isOk());
    }

    @Test
    public void testUserApplyTimeAlreadyOccupied() throws Exception {
        Job job = jobRepository.findByJobId(goodJobId);
        JSONObject req = new JSONObject()
                .fluentPut("job_id", goodJobId)
                .fluentPut("cv_id", userCVId)
                .fluentPut("begin_date", job.getBeginDate().toString())
                .fluentPut("end_date",
                        Date.valueOf(job.getBeginDate().toLocalDate().plusDays(1)).toString());
        mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().isOk());
        // mock that merchant user approved application
        WechatUser user = wechatUserRepository.getOne(1);
        Application application = applicationRepository.findByWechatUserAndJob(user, job);
        application.setStatus(true);
        applicationRepository.save(application);
        // another user applied
        req = new JSONObject()
                .fluentPut("job_id", goodJob2Id)
                .fluentPut("cv_id", userCVId)
                .fluentPut("begin_date", job.getBeginDate())
                .fluentPut("end_date", job.getEndDate());
        mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().isBadRequest());
    }

} 
