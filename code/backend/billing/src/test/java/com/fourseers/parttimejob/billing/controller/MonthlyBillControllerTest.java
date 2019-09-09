package com.fourseers.parttimejob.billing.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.billing.repository.CompanyRepository;
import com.fourseers.parttimejob.billing.repository.MonthlyBillRepository;
import com.fourseers.parttimejob.common.entity.Company;
import com.fourseers.parttimejob.common.entity.MonthlyBill;
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
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MonthlyBillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MonthlyBillRepository monthlyBillRepository;

    @Autowired
    private CompanyRepository companyRepository;

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

        Company company = companyRepository.findByManager(bossname);

        MonthlyBill monthlyBill = monthlyBillRepository.findByCompanyAndYearAndMonth(company, 2019, 7);
        assertNotNull(monthlyBill);
        assertEquals(Integer.valueOf(2019), monthlyBill.getYear());
        assertEquals(Integer.valueOf(7), monthlyBill.getMonth());
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

    @Test
    public void getMonthlyBillStatusPendingSuccess() throws Exception {
        String bossname = "罗永浩";

        MvcResult result = mockMvc.perform(get("/merchant/billing/monthly-pay")
                .header("x-internal-token", bossname)
                .param("year", "2019")
                .param("month", "8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));
        assertEquals("pending", response.getString("data"));
    }

    @Test
    public void getMonthlyBillStatusUnpaidSuccess() throws Exception {
        String bossname = "罗永浩";

        MvcResult result = mockMvc.perform(get("/merchant/billing/monthly-pay")
                .header("x-internal-token", bossname)
                .param("year", "2019")
                .param("month", "7")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));
        assertEquals("unpaid", response.getString("data"));
    }

    @Test
    public void getMonthlyBillStatusNoBillThenPaid() throws Exception {
        String bossname = "罗永浩";

        MvcResult result = mockMvc.perform(get("/merchant/billing/monthly-pay")
                .header("x-internal-token", bossname)
                .param("year", "2017")
                .param("month", "8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));
        assertEquals("paid", response.getString("data"));
    }

    @Test
    public void getMonthlyBillNoCompany() throws Exception {
        String bossname = "poor user";

        MvcResult result = mockMvc.perform(get("/merchant/billing/monthly-pay")
                .header("x-internal-token", bossname)
                .param("year", "2019")
                .param("month", "7")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("user does not belong to any company", response.getString("message"));
    }
}
