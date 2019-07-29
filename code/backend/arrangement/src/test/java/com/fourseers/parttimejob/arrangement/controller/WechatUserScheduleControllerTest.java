package com.fourseers.parttimejob.arrangement.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.arrangement.repository.ApplicationRepository;
import com.fourseers.parttimejob.arrangement.repository.CVRepository;
import com.fourseers.parttimejob.arrangement.repository.JobRepository;
import com.fourseers.parttimejob.arrangement.repository.WechatUserRepository;
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
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class WechatUserScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CVRepository cvRepository;

    @Autowired
    private WechatUserRepository wechatUserRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Value("${app.wechat_user_prefix}")
    private String WECHAT_USER_PREFIX;

    private Integer userId;
    private String cvId;
    private Integer jobId;
    private Integer applicationId;
    private String userToken;

    @Before
    public void before() throws Exception {

        userId = 1;
        jobId = 1;

        WechatUser user = wechatUserRepository.getOne(userId);
        userToken = WECHAT_USER_PREFIX + user.getOpenid();
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
        cvId = cv.getId();

        // apply for one job
        Job job = jobRepository.getOne(jobId);
        Application application = new Application();
        application.setStatus(true);    // approved
        application.setJob(job);
        application.setCvId(cvId);
        application.setAppliedBeginDate(job.getBeginDate());
        application.setAppliedEndDate(job.getEndDate());
        application.setWechatUser(user);
        applicationRepository.save(application);
        applicationId = application.getApplicationId();
    }

    @After
    public void after() throws Exception {
        cvRepository.deleteAll();
        applicationRepository.deleteAll();
    }

    @Test
    public void testGetScheduleSuccess() throws Exception {
        MvcResult result = mockMvc.perform(get("/user/schedule")
                .header("x-internal-token", userToken)
                .param("begin_date", "2017-07-01")
                .param("end_date", "2017-07-31"))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        JSONArray data = response.getJSONArray("data");
        assertNotNull(data);
        assertEquals(1, data.size());
        JSONObject entry = data.getJSONObject(0);
        assertNotNull(entry);
        assertEquals(jobId, entry.getInteger("job_id"));

        Application application = applicationRepository.getOne(applicationId);
        assertEquals(application.getAppliedBeginDate(), entry.getSqlDate("begin_date"));
        assertEquals(application.getAppliedEndDate(), entry.getSqlDate("end_date"));
        assertNotNull(entry.getInteger("shop_id"));
        assertNotNull(entry.getString("shop_name"));
    }

    @Test
    public void testGetScheduleInvalidDate() throws Exception {
        mockMvc.perform(get("/user/schedule")
                .header("x-internal-token", userToken)
                .param("begin_date", "2017-07-31")
                .param("end_date", "2017-07-01"))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testGetScheduleInvalidUserToken() throws Exception {
        mockMvc.perform(get("/user/schedule")
                .header("x-internal-token", userToken + "asdf")
                .param("begin_date", "2017-07-31")
                .param("end_date", "2017-07-01"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetScheduleWrongUserTokenFormat() throws Exception {
        mockMvc.perform(get("/user/schedule")
                .header("x-internal-token", "whatever")
                .param("begin_date", "2017-07-31")
                .param("end_date", "2017-07-01"))
                .andExpect(status().isBadRequest());
    }


} 
