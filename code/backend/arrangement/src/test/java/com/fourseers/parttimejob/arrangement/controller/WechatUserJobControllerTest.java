package com.fourseers.parttimejob.arrangement.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.arrangement.dao.CVDao;
import com.fourseers.parttimejob.arrangement.repository.CVRepository;
import com.fourseers.parttimejob.arrangement.repository.JobRepository;
import com.fourseers.parttimejob.arrangement.repository.ShopRepository;
import com.fourseers.parttimejob.common.entity.CV;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.Shop;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.apache.logging.log4j.message.TimestampMessage;
import org.bouncycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi;
import org.bouncycastle.util.Times;
import org.hibernate.Hibernate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import sun.awt.geom.AreaOp;

import javax.transaction.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.DATE;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class WechatUserJobControllerTest {

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

    private String userHeader;
    private String invalidUserHeader;
    private String userCVId;
    private Integer goodJobId, noEduJobId, outdateJobId, fullJobId, femaleJobId;

    private int newJobId;

    @Value("${app.wechat_user_prefix}")
    private String WECHAT_USER_PREFIX;


    @Before
    public void before() throws Exception {

        userHeader = WECHAT_USER_PREFIX + "fakeOpenid";
        invalidUserHeader = WECHAT_USER_PREFIX + "wrongOpenid";

        CV cv = new CV();
        cv.setUserId(1);
        cv.setEducation("高中毕业");
        cv.setContent("Default content goes here...");
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
        job.setBeginApplyDate(yesterday);
        job.setEndApplyDate(tomorrow);
        job.setBeginDate(yesterdayNextWeek);
        job.setEndDate(tomorrowNextWeek);
        job.setSalary(100.0);
        job.setEducation("高中毕业");
        job.setShop(shopRepository.getOne(1));
        job.setJobName("Teach abc");
        job.setNeedGender(2);
        jobRepository.save(job);
        goodJobId = job.getJobId();

        job = new Job();
        job.setNeedAmount(50);
        job.setJobDetail("job detail...");
        job.setBeginApplyDate(yesterday);
        job.setEndApplyDate(tomorrow);
        job.setBeginDate(yesterdayNextWeek);
        job.setEndDate(tomorrowNextWeek);
        job.setSalary(100.0);
        job.setEducation("本科毕业");
        job.setShop(shopRepository.getOne(1));
        job.setJobName("Teach abc");
        job.setNeedGender(2);
        jobRepository.save(job);
        noEduJobId = job.getJobId();

        job = new Job();
        job.setNeedAmount(50);
        job.setJobDetail("job detail...");
        job.setBeginApplyDate(yesterdayLastWeek);
        job.setEndApplyDate(tomorrowLastWeek);
        job.setBeginDate(yesterdayNextWeek);
        job.setEndDate(tomorrowNextWeek);
        job.setSalary(100.0);
        job.setEducation("高中毕业");
        job.setShop(shopRepository.getOne(1));
        job.setJobName("Teach abc");
        job.setNeedGender(2);
        jobRepository.save(job);
        outdateJobId = job.getJobId();

        job = new Job();
        job.setNeedAmount(50);
        job.setAppliedAmount(50);
        job.setJobDetail("job detail...");
        job.setBeginApplyDate(yesterday);
        job.setEndApplyDate(tomorrow);
        job.setBeginDate(yesterdayNextWeek);
        job.setEndDate(tomorrowNextWeek);
        job.setSalary(100.0);
        job.setEducation("高中毕业");
        job.setShop(shopRepository.getOne(1));
        job.setJobName("Teach abc");
        job.setNeedGender(2);
        jobRepository.save(job);
        fullJobId = job.getJobId();

        job = new Job();
        job.setNeedAmount(50);
        job.setJobDetail("job detail...");
        job.setBeginApplyDate(yesterday);
        job.setEndApplyDate(tomorrow);
        job.setBeginDate(yesterdayNextWeek);
        job.setEndDate(tomorrowNextWeek);
        job.setSalary(100.0);
        job.setEducation("高中毕业");
        job.setShop(shopRepository.getOne(1));
        job.setJobName("Teach English");
        job.setNeedGender(0);   // require female
        jobRepository.save(job);
        femaleJobId = job.getJobId();
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
            float cur = Math.abs(shop.getLatitude() - latitude) +
                    Math.abs(shop.getLongitude() - longitude);
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
                .param("jobId", "1")
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
        assertNotNull(data.getString("begin_apply_date"));
        assertNotNull(data.getString("end_apply_date"));
    }


    @Test
    public void testGetJobInvalidJobId() throws Exception {
        mockMvc.perform(get("/user/job")
                .param("jobId", "-1")
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
                .param("jobId", "1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetJobInvalidAuthToken() throws Exception {
        mockMvc.perform(get("/user/job")
                .param("jobId", "1")
                .header("x-internal-token", invalidUserHeader))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testUserApplyJobSuccess() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("jobId", goodJobId)
                .fluentPut("cvId", userCVId);
        MvcResult result = mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    public void testUserApplyJobNoJobId() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("cvId", userCVId);
        MvcResult result = mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testUserApplyJobNoCV() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("jobId", goodJobId);
        MvcResult result = mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testUserApplyJobMissedApplicationDate() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("jobId", outdateJobId)
                .fluentPut("cvId", userCVId);
        MvcResult result = mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testUserApplyJobNoEdu() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("jobId", noEduJobId)
                .fluentPut("cvId", userCVId);
        MvcResult result = mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }


    @Test
    public void testUserApplyJobInvalidCV() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("jobId", noEduJobId)
                .fluentPut("cvId", 10000);
        MvcResult result = mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testUserApplyJobInvalidJob() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("jobId", 1000)
                .fluentPut("cvId", userCVId);
        MvcResult result = mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testUserApplyJobNoMoreSeats() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("jobId", fullJobId)
                .fluentPut("cvId", userCVId);
        MvcResult result = mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testUserApplyJobDifferentGender() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("jobId", femaleJobId)
                .fluentPut("cvId", userCVId);
        MvcResult result = mockMvc.perform(post("/user/apply")
                .content(req.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-internal-token", userHeader))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

} 