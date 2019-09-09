package com.fourseers.parttimejob.billing.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.billing.repository.*;
import com.fourseers.parttimejob.common.entity.Billing;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class WechatUserBillingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BillingRepository billingRepository;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    WechatUserRepository wechatUserRepository;

    @Autowired
    WorkRepository workRepository;

    @Value("${app.wechat_user_prefix}")
    String WECHAT_USER_PREFIX;

    private Job job;
    private WechatUser wechatUser;
    private String userToken;
    private Billing bill;
    private Work work;

    @Before
    public void before() throws Exception {
        job = jobRepository.getOne(1);
        wechatUser = wechatUserRepository.getOne(1);
        userToken = WECHAT_USER_PREFIX + wechatUserRepository.getOne(1).getOpenid();
        // data.sql have four work entries and two bill entries
        // two bill entries have 100 + 100 payment.
    }

    @After
    public void after() throws Exception {
        // delete that
        workRepository.deleteAll();
        billingRepository.deleteAll();
    }

    @Test
    public void testGetUserBillsSuccess() throws Exception {

        // already four work entries, with workId = 1 or 3 have bill

        MvcResult result = mockMvc.perform(get("/user/works")
                .header("x-internal-token", userToken))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        JSONObject data = response.getJSONObject("data");
        assertNotNull(data);
        JSONArray content = data.getJSONArray("content");
        assertNotNull(content);
        assertEquals(4, content.size());
        for(JSONObject obj: content.toJavaList(JSONObject.class)) {
            assertNotNull(obj.getInteger("work_id"));
            assertNotNull(obj.getSqlDate("work_date"));
            assertNotNull(obj.getBoolean("salary_confirmed"));
            assertNotNull(obj.getBoolean("rejected"));
            if(obj.getBoolean("salary_confirmed") == true) {
                assertNotNull(obj.getInteger("bill_id"));
                assertNotNull(obj.getDouble("payment"));
            }
        }
        }

    @Test
    public void testGetUserBillsInvalidPageCount() throws Exception {
        mockMvc.perform(get("/user/works")
                .param("pageCount", "-1")
                .header("x-internal-token", userToken))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetUserBalance() throws Exception {
        MvcResult result = mockMvc.perform(get("/user/balance")
                .header("x-internal-token", userToken))
                .andExpect(status().isOk()).andReturn();

        JSONObject response = JSONObject.parseObject(result.getResponse().getContentAsString());
        Double balance = response.getDouble("data");
        assertEquals(Double.valueOf(200.0), balance);
    }

    @Test
    public void testDrawAll() throws Exception {
        MvcResult result = mockMvc.perform(post("/user/drawAll")
                .header("x-internal-token", userToken))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSONObject.parseObject(result.getResponse().getContentAsString());
        JSONObject data = response.getJSONObject("data");
        assertNotNull(data);

        assertTrue(data.getBoolean("success"));
        assertEquals(Double.valueOf(200.0), data.getDouble("payment"));

        // draw again to see if it would success again
        result = mockMvc.perform(post("/user/drawAll")
                .header("x-internal-token", userToken))
                .andExpect(status().isOk())
                .andReturn();
        response = JSONObject.parseObject(result.getResponse().getContentAsString());
        data = response.getJSONObject("data");
        assertNotNull(data);
        assertFalse(data.getBoolean("success"));
        assertEquals(Double.valueOf(0.0), data.getDouble("payment"));
    }

    @Test
    public void testGetDrawHistory() throws Exception {
        // draw first
        mockMvc.perform(post("/user/drawAll")
                .header("x-internal-token", userToken))
                .andExpect(status().isOk())
                .andReturn();

        // get list
        MvcResult result = mockMvc.perform(get("/user/draw-history")
                .header("x-internal-token", userToken))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSONObject.parseObject(result.getResponse().getContentAsString());
        JSONObject data = response.getJSONObject("data");
        assertNotNull(data);
        assertEquals(Integer.valueOf(2), data.getInteger("total_elements"));
        JSONArray content = data.getJSONArray("content");
        assertNotNull(content);
        assertEquals(Double.valueOf(100.0), content.getJSONObject(0).getDouble("payment"));
        assertEquals(Double.valueOf(100.0), content.getJSONObject(1).getDouble("payment"));
    }
} 
