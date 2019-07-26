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

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BillingControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
        body.fluentPut("work_id", 2)
            .fluentPut("payment", 100)
            .fluentPut("method", "微信支付")
            .fluentPut("meta", null);
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
        body.fluentPut("work_id", 666)
            .fluentPut("payment", 100)
            .fluentPut("method", "微信支付")
            .fluentPut("meta", null);
        MvcResult result = mockMvc.perform(post("/merchant/billing/pay")
                .header("x-internal-token", bossname)
                .content(body.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("work not exist or not belong to current company", response.getString("message"));
    }

    @Test
    public void payBillAlreadyPaid() throws Exception {
        String bossname = "罗永浩";

        JSONObject body = new JSONObject();
        body.fluentPut("work_id", 1)
            .fluentPut("payment", 100)
            .fluentPut("method", "微信支付")
            .fluentPut("meta", null);
        MvcResult result = mockMvc.perform(post("/merchant/billing/pay")
                .header("x-internal-token", bossname)
                .content(body.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("work already paid", response.getString("message"));
    }

    @Test
    public void payBillUserNoCompany() throws Exception {
        String bossname = "Poor user";

        JSONObject body = new JSONObject();
        body.fluentPut("work_id", 2)
            .fluentPut("payment", 100)
            .fluentPut("method", "微信支付")
            .fluentPut("meta", null);
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
        body.fluentPut("work_id", 2)
            .fluentPut("payment", 100)
            .fluentPut("method", "微信支付")
            .fluentPut("meta", null);
        MvcResult result = mockMvc.perform(post("/merchant/billing/pay")
                .header("x-internal-token", bossname)
                .content(body.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("work not exist or not belong to current company", response.getString("message"));
    }

    @Test
    public void getBillAmountOnePartsSuccess() throws Exception {
        String bossname = "罗永浩";

        MvcResult result = mockMvc.perform(get("/merchant/billing/sum")
                .header("x-internal-token", bossname)
                .param("from", "2019-07-15")
                .param("to", "2019-07-24")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));
        assertEquals(Double.valueOf(100d), response.getJSONObject("data").getDouble("amount"));

    }

    @Test
    public void getBillAmountMorePartSuccess() throws Exception {
        String bossname = "罗永浩";

        MvcResult result = mockMvc.perform(get("/merchant/billing/sum")
                .header("x-internal-token", bossname)
                .param("from", "2019-07-15")
                .param("to", "2019-08-24")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));
        assertEquals(Double.valueOf(200d), response.getJSONObject("data").getDouble("amount"));

    }

    @Test
    public void getBillAmountIncorrectParamSuccess() throws Exception {
        String bossname = "罗永浩";

        MvcResult result = mockMvc.perform(get("/merchant/billing/sum")
                .header("x-internal-token", bossname)
                .param("from", "2019-07-15")
                .param("to", "2018-08-24")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("incorrect param", response.getString("message"));

    }

    @Test
    public void getBillAmountZero() throws Exception {
        String bossname = "罗永浩";

        MvcResult result = mockMvc.perform(get("/merchant/billing/sum")
                .header("x-internal-token", bossname)
                .param("from", "2018-07-15")
                .param("to", "2018-08-24")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));
        assertEquals(Double.valueOf(0), response.getJSONObject("data").getDouble("amount"));

    }

    @Test
    public void getBillNoCompany() throws Exception {
        String bossname = "poor user";

        MvcResult result = mockMvc.perform(get("/merchant/billing/sum")
                .header("x-internal-token", bossname)
                .param("from", "2019-07-15")
                .param("to", "2019-08-24")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("user does not belong to any company", response.getString("message"));

    }

    @Test
    public void payMonthlyBillSuccess() throws Exception {
        String bossname = "罗永浩";

        JSONObject body = new JSONObject();
        body.put("year", 2019);
        body.put("month", 7);
        MvcResult result = mockMvc.perform(post("/merchant/billing/monthly-pay")
                .header("x-internal-token", bossname)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));
    }

    @Test
    public void payMonthlyBillNoBill() throws Exception {
        String bossname = "罗永浩";

        JSONObject body = new JSONObject();
        body.put("year", 2019);
        body.put("month", 1);
        MvcResult result = mockMvc.perform(post("/merchant/billing/monthly-pay")
                .header("x-internal-token", bossname)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("nothing to pay", response.getString("message"));
    }

    @Test
    public void payMonthlyBillNoCompany() throws Exception {
        String bossname = "poor user";

        JSONObject body = new JSONObject();
        body.put("year", 2019);
        body.put("month", 7);
        MvcResult result = mockMvc.perform(post("/merchant/billing/monthly-pay")
                .header("x-internal-token", bossname)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("user does not belong to any company", response.getString("message"));
    }
}
