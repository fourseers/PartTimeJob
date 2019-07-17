package com.fourseers.parttimejob.arrangement.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.arrangement.repository.JobRepository;
import com.fourseers.parttimejob.arrangement.repository.ShopRepository;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.Shop;
import org.hibernate.Hibernate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

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

    private String userHeader;

    @Value("${app.wechat_user_prefix}")
    private String WECHAT_USER_PREFIX;

    @Before
    public void before() throws Exception {
        userHeader = WECHAT_USER_PREFIX + "fakeOpenid";
    }

    @After
    public void after() throws Exception {
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
            prev = cur;
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

} 
