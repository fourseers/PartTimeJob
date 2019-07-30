package com.fourseers.parttimejob.arrangement.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * WechatUserJobController Tester.
 *
 * @author <Authors name>
 * @since <pre>07/16/2019</pre>
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class WechatUserJobControllerTest {

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
        cvRepository.deleteById(userCVId);
    }

    /**
     *
     * Method: getJobList(@RequestParam(required = false) Float longitude, @RequestParam(required = false) Float latitude, @RequestHeader(defaultValue = "0") int pageCount, @RequestHeader("x-internal-token") String token)
     *
     */
    @Test
    public void testGetJobListSuccess() throws Exception {
        MvcResult result = mockMvc.perform(get("/user/jobs")
                .header("x-internal-token", userHeader))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        JSONObject data = response.getJSONObject("data");
        JSONArray content = data.getJSONArray("content");
        // pagination
        assertTrue(content.size() < 20);
        // content based on ids
        assertEquals(1, content.getJSONObject(0).getIntValue("job_id"));
    }

    @Transactional
    @Test
    public void testGetJobListViaGeoLocation() throws Exception {
        float longitude = 120, latitude = 30;
        MvcResult result = mockMvc.perform(get("/user/jobs")
                .param("longitude", String.valueOf(longitude))
                .param("latitude", String.valueOf(latitude))
                .header("x-internal-token", userHeader))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        JSONObject data = response.getJSONObject("data");
        JSONArray content = data.getJSONArray("content");
        // pagination
        assertTrue(content.size() < 20);
        // content based on geolocation
        float prev = 0;
        // test sorting
        for (int i = 0; i < content.size(); i++) {
            JSONObject curObj = content.getJSONObject(i);
            // figure out if it's the right order
            Integer jobId = curObj.getInteger("job_id");
            assertNotNull(jobId);
            Job job = jobRepository.getOne(jobId);
            assertNotNull(job);
            Shop shop = job.getShop();
            float cur = Math.abs(shop.getLatitude().floatValue() - latitude) +
                    Math.abs(shop.getLongitude().floatValue() - longitude);
            assertTrue(cur >= prev);
        }
    }

    @Test
    public void testGetJobListIncompleteGeoLocation() throws Exception {
        mockMvc.perform(get("/user/jobs")
            .header("x-internal-token", userHeader)
            .param("longitude", "20"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetJobListInvalidGeoLocation() throws Exception {

        mockMvc.perform(get("/user/jobs")
                .header("x-internal-token", userHeader)
                .param("longitude", "1000")
                .param("latitude", "20"))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(get("/user/jobs")
                .header("x-internal-token", userHeader)
                .param("longitude", "40")
                .param("latitude", "2000"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetJobListInvalidUserToken() throws Exception {
        mockMvc.perform(get("/user/jobs")
                .header("x-internal-token", "INVALID_FORMAT_VALUE")
                .param("longitude", "10")
                .param("latitude", "10"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetJobListWrongUserToken() throws Exception {
        mockMvc.perform(get("/user/jobs")
                .header("x-internal-token", invalidUserHeader)
                .param("longitude", "10")
                .param("latitude", "10"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetJobSuccess() throws Exception {
        MvcResult result = mockMvc.perform(get("/user/job")
                .param("job_id", goodJobId.toString())
                .header("x-internal-token", userHeader))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        JSONObject data = response.getJSONObject("data");
        assertNotNull(data);
        JSONObject shop = data.getJSONObject("shop");
        assertNotNull(shop);
        assertNotNull(shop.getFloat("longitude"));
        assertNotNull(shop.getFloat("latitude"));
        assertNotNull(shop.getString("address"));
        assertNotNull(data.getString("job_detail"));
        assertNotNull(data.getString("begin_apply_time"));
        assertNotNull(data.getString("end_apply_time"));
        assertNotNull(data.getString("begin_time"));
        assertNotNull(data.getString("end_time"));
    }


    @Test
    public void testGetJobInvalidJobId() throws Exception {
        mockMvc.perform(get("/user/job")
                .param("job_id", "-1")
                .header("x-internal-token", userHeader))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetJobNoJobId() throws Exception {
        mockMvc.perform(get("/user/job")
                .header("x-internal-token", userHeader))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetJobNoAuthToken() throws Exception {
        mockMvc.perform(get("/user/job")
                .param("job_id", goodJobId.toString()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetJobInvalidAuthToken() throws Exception {
        mockMvc.perform(get("/user/job")
                .param("job_id", goodJobId.toString())
                .header("x-internal-token", invalidUserHeader))
                .andExpect(status().isForbidden());
    }
}
