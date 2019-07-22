package com.fourseers.parttimejob.billing.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BillingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getBillingsOnePageSuccess() throws Exception {
        String bossname = "罗永浩";

        MvcResult result = mockMvc.perform(get("/merchant/billing")
                .header("x-internal-token", bossname)
                .param("page_count", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));
        assertEquals("锤科总部", response.getJSONObject("data").getJSONArray("content").getJSONObject(0).getString("shop_name"));
    }

    @Test
    public void getBillingsPageOverflow() throws Exception {
        String bossname = "罗永浩";

        MvcResult result = mockMvc.perform(get("/merchant/billing")
                .header("x-internal-token", bossname)
                .param("page_count", "666")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("no bills", response.getString("message"));
    }

    @Test
    public void getBillingsPageNegative() throws Exception {
        String bossname = "罗永浩";

        MvcResult result = mockMvc.perform(get("/merchant/billing")
                .header("x-internal-token", bossname)
                .param("page_count", "-1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("incorrect param", response.getString("message"));
    }

    @Test
    public void getBillingsPageNoCompany() throws Exception {
        String bossname = "poor user";

        MvcResult result = mockMvc.perform(get("/merchant/billing")
                .header("x-internal-token", bossname)
                .param("page_count", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("user does not belong to any company", response.getString("message"));
    }

    @Test
    public void payBillSuccess() throws Exception {
        String bossname = "罗永浩";

        JSONObject body = new JSONObject();
        body.put("bill_id", 2);
        MvcResult result = mockMvc.perform(post("/merchant/billing/pay")
                .header("x-internal-token", bossname)
                .content(body.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));
    }

    @Test
    public void payBillNotExist() throws Exception {
        String bossname = "罗永浩";

        JSONObject body = new JSONObject();
        body.put("bill_id", 666);
        MvcResult result = mockMvc.perform(post("/merchant/billing/pay")
                .header("x-internal-token", bossname)
                .content(body.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("bill not exist or not belong to current company", response.getString("message"));
    }

    @Test
    public void payBillAlreadyPaid() throws Exception {
        String bossname = "罗永浩";

        JSONObject body = new JSONObject();
        body.put("bill_id", 1);
        MvcResult result = mockMvc.perform(post("/merchant/billing/pay")
                .header("x-internal-token", bossname)
                .content(body.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("bill already paid", response.getString("message"));
    }

    @Test
    public void payBillUserNoCompany() throws Exception {
        String bossname = "Poor user";

        JSONObject body = new JSONObject();
        body.put("bill_id", 2);
        MvcResult result = mockMvc.perform(post("/merchant/billing/pay")
                .header("x-internal-token", bossname)
                .content(body.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("user does not belong to any company", response.getString("message"));
    }

    @Test
    public void payBillNotBelongTo() throws Exception {
        String bossname = "Tim Cook";

        JSONObject body = new JSONObject();
        body.put("bill_id", 2);
        MvcResult result = mockMvc.perform(post("/merchant/billing/pay")
                .header("x-internal-token", bossname)
                .content(body.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("bill not exist or not belong to current company", response.getString("message"));
    }
}
