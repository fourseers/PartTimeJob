package com.fourseers.parttimejob.arrangement.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.arrangement.repository.JobRepository;
import com.fourseers.parttimejob.arrangement.repository.ShopRepository;
import com.fourseers.parttimejob.arrangement.repository.WechatUserRepository;
import com.fourseers.parttimejob.arrangement.repository.WorkRepository;
import com.fourseers.parttimejob.common.entity.Etc;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.common.entity.Work;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class WechatCheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private WechatUserRepository wechatUserRepository;

    @Value("${app.wechat_user_prefix}")
    private String WECHAT_USER_PREFIX;

    private Integer userId;
    private String userHeader;
    private Integer shopId;
    private String invalidUserHeader;

    private Job job;
    private WechatUser wechatUser;

    @Before
    public void before() throws Exception {
        userHeader = WECHAT_USER_PREFIX + "fakeOpenid";
        invalidUserHeader = WECHAT_USER_PREFIX + "wrongOpenid";
        shopId = 1;
        userId = 1;

        wechatUser = wechatUserRepository.getOne(userId);
        job = new Job();
        job.setNeedAmount(50);
        job.setJobDetail("job detail...");
        job.setBeginApplyTime(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
        job.setEndApplyTime(Timestamp.valueOf(LocalDateTime.now().plusDays(1)));
        job.setBeginDate(Date.valueOf(LocalDate.now().minusDays(1)));
        job.setEndDate(Date.valueOf(LocalDate.now().plusDays(1)));
        job.setSalary(100.0);
        job.setEducation(Etc.Education.SENIOR_HIGH);
        job.setShop(shopRepository.getOne(shopId));
        // actually time here doesn't matter
        job.setBeginTime(Time.valueOf(LocalTime.of(6, 0)));
        job.setEndTime(Time.valueOf(LocalTime.of(18, 0)));
        job.setJobName("Teach abc");
        job.setNeedGender(2);

        job.setBeginTime(Time.valueOf(LocalTime.now().minusMinutes(10)));
        job.setEndTime(Time.valueOf(LocalTime.now().plusMinutes(10)));
        jobRepository.save(job);
    }

    @After
    public void after() throws Exception {
        workRepository.deleteAll();
        jobRepository.deleteAll();
    }

    /**
     *
     * Method: checkIn(@ApiParam @RequestBody CheckinDto params, @ApiParam(hidden = true) @RequestHeader("x-internal-token") String token)
     *
     */
    @Test
    public void testCheckInSuccess() throws Exception {
        // pre conditions
        Work work = new Work();
        work.setJob(job);
        work.setWorkDate(Date.valueOf(LocalDate.now()));
        work.setWorker(wechatUser);
        work.setExpectedCheckin(Time.valueOf(LocalTime.now().minusMinutes(10)));
        work.setExpectedCheckout(Time.valueOf(LocalTime.now().plusMinutes(10)));
        workRepository.save(work);

        // should work
        JSONObject req = new JSONObject()
                .fluentPut("job_id", job.getJobId())
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
        // pre conditions
        Work work = new Work();
        work.setJob(job);
        work.setWorkDate(Date.valueOf(LocalDate.now()));
        work.setWorker(wechatUser);
        work.setExpectedCheckin(Time.valueOf(LocalTime.now().minusMinutes(10)));
        work.setExpectedCheckout(Time.valueOf(LocalTime.now().plusMinutes(10)));
        workRepository.save(work);

        JSONObject req = new JSONObject()
                .fluentPut("job_id", job.getJobId())
                .fluentPut("longitude", 120);
        mockMvc.perform(post("/user/checkin")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCheckInInvalidLocation() throws Exception {
        // pre conditions
        Work work = new Work();
        work.setJob(job);
        work.setWorkDate(Date.valueOf(LocalDate.now()));
        work.setWorker(wechatUser);
        work.setExpectedCheckin(Time.valueOf(LocalTime.now().minusMinutes(10)));
        work.setExpectedCheckout(Time.valueOf(LocalTime.now().plusMinutes(10)));
        workRepository.save(work);

        JSONObject req = new JSONObject()
                .fluentPut("job_id", job.getJobId())
                .fluentPut("latitude", 300)
                .fluentPut("longitude", 120);
        MvcResult result = mockMvc.perform(post("/user/checkin")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        result.getResponse().getContentAsString();
    }

    @Test
    public void testCheckInImproperTime() throws Exception {
        // pre conditions
        Work work = new Work();
        work.setJob(job);
        work.setWorkDate(Date.valueOf(LocalDate.now()));
        work.setWorker(wechatUser);
        work.setExpectedCheckin(Time.valueOf(LocalTime.now().minusMinutes(30)));
        work.setExpectedCheckout(Time.valueOf(LocalTime.now().minusMinutes(20)));
        workRepository.save(work);

        JSONObject req = new JSONObject()
                .fluentPut("job_id", job.getJobId())
                .fluentPut("longitude", 120.0f)
                .fluentPut("latitude", 30.0f);
        mockMvc.perform(post("/user/checkin")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testCheckInImproperLocation() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("job_id", job.getJobId())
                .fluentPut("longitude", 120.1)
                .fluentPut("latitude", 30.1); // 14 km away
        mockMvc.perform(post("/user/checkin")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCheckInAlreadyChecked() throws Exception {
        // pre conditions
        Work work = new Work();
        work.setJob(job);
        work.setWorkDate(Date.valueOf(LocalDate.now()));
        work.setWorker(wechatUser);
        work.setExpectedCheckin(Time.valueOf(LocalTime.now().minusMinutes(10)));
        work.setExpectedCheckout(Time.valueOf(LocalTime.now().plusMinutes(10)));
        work.setCheckin(Time.valueOf(LocalTime.now().minusMinutes(5)));
        workRepository.save(work);

        JSONObject req = new JSONObject()
                .fluentPut("job_id", job.getJobId())
                .fluentPut("longitude", 120)
                .fluentPut("latitude", 30);
        mockMvc.perform(post("/user/checkin")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCheckOutSuccess() throws Exception {
        // pre conditions
        Work work = new Work();
        work.setJob(job);
        work.setWorkDate(Date.valueOf(LocalDate.now()));
        work.setWorker(wechatUser);
        work.setExpectedCheckin(Time.valueOf(LocalTime.now().minusMinutes(10)));
        work.setExpectedCheckout(Time.valueOf(LocalTime.now().plusMinutes(10)));
        work.setCheckin(Time.valueOf(LocalTime.now().minusMinutes(5)));
        workRepository.save(work);

        JSONObject req = new JSONObject()
                .fluentPut("job_id", job.getJobId())
                .fluentPut("longitude", 120)
                .fluentPut("latitude", 30);

        mockMvc.perform(post("/user/checkout")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCheckOutNotCheckedInYet() throws Exception {
        // pre conditions
        Work work = new Work();
        work.setJob(job);
        work.setWorkDate(Date.valueOf(LocalDate.now()));
        work.setWorker(wechatUser);
        work.setExpectedCheckin(Time.valueOf(LocalTime.now().minusMinutes(10)));
        work.setExpectedCheckout(Time.valueOf(LocalTime.now().plusMinutes(10)));
        workRepository.save(work);

        JSONObject req = new JSONObject()
                .fluentPut("job_id", job.getJobId())
                .fluentPut("longitude", 120)
                .fluentPut("latitude", 30);

        mockMvc.perform(post("/user/checkout")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCheckOutImproperLocation() throws Exception {
        // pre conditions
        Work work = new Work();
        work.setJob(job);
        work.setWorkDate(Date.valueOf(LocalDate.now()));
        work.setWorker(wechatUser);
        work.setExpectedCheckin(Time.valueOf(LocalTime.now().minusMinutes(10)));
        work.setExpectedCheckout(Time.valueOf(LocalTime.now().plusMinutes(10)));
        work.setCheckin(Time.valueOf(LocalTime.now().minusMinutes(5)));
        workRepository.save(work);

        JSONObject req = new JSONObject()
                .fluentPut("job_id", job.getJobId())
                // 14 km away
                .fluentPut("longitude", 120.1)
                .fluentPut("latitude", 30.1);
        mockMvc.perform(post("/user/checkout")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testCheckOutImproperTime() throws Exception {
        // pre conditions
        Work work = new Work();
        work.setJob(job);
        work.setWorkDate(Date.valueOf(LocalDate.now()));
        work.setWorker(wechatUser);
        work.setExpectedCheckin(Time.valueOf(LocalTime.now().minusMinutes(30)));
        work.setExpectedCheckout(Time.valueOf(LocalTime.now().minusMinutes(20)));
        work.setCheckin(Time.valueOf(LocalTime.now().minusMinutes(30)));
        workRepository.save(work);

        JSONObject req = new JSONObject()
                .fluentPut("job_id", job.getJobId())
                .fluentPut("longitude", 120.0f)
                .fluentPut("latitude", 30.0f);

        mockMvc.perform(post("/user/checkout")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testCheckOutAlreadyCheckOut() throws Exception {
        // pre conditions
        Work work = new Work();
        work.setJob(job);
        work.setWorkDate(Date.valueOf(LocalDate.now()));
        work.setWorker(wechatUser);
        work.setExpectedCheckin(Time.valueOf(LocalTime.now().minusMinutes(10)));
        work.setExpectedCheckout(Time.valueOf(LocalTime.now().plusMinutes(10)));
        work.setCheckin(Time.valueOf(LocalTime.now().minusMinutes(5)));
        work.setCheckout(Time.valueOf(LocalTime.now()));
        workRepository.save(work);

        JSONObject req = new JSONObject()
                .fluentPut("job_id", job.getJobId())
                .fluentPut("longitude", 120)
                .fluentPut("latitude", 30);
        mockMvc.perform(post("/user/checkout")
                .header("x-internal-token", userHeader)
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCheckStatusSuccess() throws Exception {
        // pre conditions
        Work work = new Work();
        work.setJob(job);
        work.setWorkDate(Date.valueOf(LocalDate.now()));
        work.setWorker(wechatUser);
        work.setExpectedCheckin(Time.valueOf(LocalTime.now().minusMinutes(10)));
        work.setExpectedCheckout(Time.valueOf(LocalTime.now().plusMinutes(10)));
        workRepository.save(work);

        MvcResult result = mockMvc.perform(get("/user/check-status")
                .param("job_id", job.getJobId().toString())
                .header("x-internal-token", userHeader))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        JSONObject data = response.getJSONObject("data");
        assertNotNull(data);
        assertEquals(work.getWorkDate().toString(), data.getString("work_date"));
        assertEquals(work.getExpectedCheckin().toString(), data.getString("expected_checkin"));
        assertEquals(work.getExpectedCheckout().toString(), data.getString("expected_checkout"));
        assertNull(work.getCheckin());
        assertNull(work.getCheckout());
    }

    @Test
    public void testCheckStatusInvalidJobId() throws Exception {
        // pre conditions
        Work work = new Work();
        work.setJob(job);
        work.setWorkDate(Date.valueOf(LocalDate.now()));
        work.setWorker(wechatUser);
        work.setExpectedCheckin(Time.valueOf(LocalTime.now().minusMinutes(10)));
        work.setExpectedCheckout(Time.valueOf(LocalTime.now().plusMinutes(10)));
        workRepository.save(work);

        mockMvc.perform(get("/user/check-status")
                .param("job_id", "100")
                .header("x-internal-token", userHeader))
                .andExpect(status().isBadRequest());
    }
} 
