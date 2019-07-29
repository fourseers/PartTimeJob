package com.fourseers.parttimejob.arrangement.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.arrangement.repository.*;
import com.fourseers.parttimejob.common.entity.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class JobControllerApplyTest {

    @Autowired
    WechatUserJobController wechatUserJobController;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CVRepository cvRepository;

    @Autowired
    private WechatUserRepository wechatUserRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    private String userHeader;
    private String invalidUserHeader;
    private String userCVId;
    private Integer goodJobId, noEduJobId, outdateJobId, fullJobId, femaleJobId;
    private Integer stoppedJobId;

    @Value("${app.wechat_user_prefix}")
    private String WECHAT_USER_PREFIX;

    @Before
    public void before() throws Exception {

        userHeader = WECHAT_USER_PREFIX + "fakeOpenid";
        invalidUserHeader = WECHAT_USER_PREFIX + "wrongOpenid";

        WechatUser user = wechatUserRepository.getOne(1);
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
        job.setEndDate(new Date(tomorrowNextWeek.getTime()));
        job.setSalary(100.0);
        job.setEducation(Etc.Education.SENIOR_HIGH);
        job.setShop(shopRepository.getOne(1));
        job.setJobName("Teach English");
        job.setBeginTime(Time.valueOf(LocalTime.of(6,0)));
        job.setEndTime(Time.valueOf(LocalTime.of(18,0)));
        job.setNeedGender(2);
        jobRepository.save(job);
        stoppedJobId = job.getJobId();

        Application application = new Application();
        application.setAppliedBeginDate(new Date(yesterdayNextWeek.getTime()));
        application.setAppliedEndDate(new Date(tomorrowNextWeek.getTime()));
        application.setCreateTime(yesterday);
        application.setCvId(userCVId);
        application.setJob(jobRepository.findByJobId(goodJobId));
        application.setStatus(false);
        application.setWechatUser(wechatUserRepository.getOne(1));
        applicationRepository.save(application);
    }

    @After
    public void after() throws Exception {
        cvRepository.deleteById(userCVId);
    }

    @Test
    public void getApplicationsSuccess() throws Exception {
        String bossname = "Tim Cook";

        MvcResult result = mockMvc.perform(get("/merchant/applications")
                .param("job_id", String.valueOf(goodJobId))
                .param("page_count", "0")
                .header("x-internal-token", bossname))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));
        assertNotNull(response.getJSONObject("data"));
        assertEquals(1l, response.getJSONObject("data").getJSONArray("content").size());
    }

    @Test
    public void getApplicationsPageNumOverflow() throws Exception {
        String bossname = "Tim Cook";

        MvcResult result = mockMvc.perform(get("/merchant/applications")
                .param("job_id", String.valueOf(goodJobId))
                .param("page_count", "666")
                .header("x-internal-token", bossname))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("application not exist", response.getString("message"));
    }

    @Test
    public void getApplicationsJobNotBelongTo() throws Exception {
        String bossname = "罗永浩";

        MvcResult result = mockMvc.perform(get("/merchant/applications")
                .param("job_id", String.valueOf(goodJobId))
                .param("page_count", "666")
                .header("x-internal-token", bossname))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("job not exist or not belong to", response.getString("message"));
    }

    @Test
    public void getApplicationsJobUserNoCompany() throws Exception {
        String bossname = "poor user";

        MvcResult result = mockMvc.perform(get("/merchant/applications")
                .param("job_id", String.valueOf(goodJobId))
                .param("page_count", "666")
                .header("x-internal-token", bossname))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("user does not belong to a company", response.getString("message"));
    }
}
