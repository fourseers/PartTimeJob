package com.fourseers.parttimejob.billing.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.billing.repository.*;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.WechatUser;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Before
    public void before() throws Exception {
        job = jobRepository.getOne(1);
        wechatUser = wechatUserRepository.getOne(1);
        userToken = WECHAT_USER_PREFIX + wechatUserRepository.getOne(1).getOpenid();
    }

    @After
    public void after() throws Exception {
        workRepository.deleteAll();
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

} 
