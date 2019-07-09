package com.fourseers.parttimejob.warehouse.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.warehouse.entity.Company;
import com.fourseers.parttimejob.warehouse.entity.MerchantUser;
import com.fourseers.parttimejob.warehouse.service.CompanyService;
import com.fourseers.parttimejob.warehouse.service.MerchantUserService;
import org.junit.Before;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CompanyControllerTest {

    @Autowired
    CompanyService companyService;

    @Autowired
    MerchantUserService merchantUserService;

    @Autowired
    MockMvc mockMvc;

    @Before
    public void setUp() {
        MerchantUser boss = new MerchantUser();
        boss.setUsername("Tim Cook");
        boss.setPassword("some password");
        Company company = new Company();
        company.setCompanyName("some_company");

        merchantUserService.save(boss);
        companyService.save(company, boss.getUserId());
    }

    @Test
    public void createCompanySuccess() throws Exception {

        JSONObject body = new JSONObject();
        body.put("company_name", "another_company");
        MvcResult result = mockMvc.perform(post("/merchant/company/")
                .header("x-internal-token", 2)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));
    }

    @Test
    public void createCompanyAlreadyExist() throws Exception {

        JSONObject body = new JSONObject();
        body.put("company_name", "some_company");
        MvcResult result = mockMvc.perform(post("/merchant/company/")
                .header("x-internal-token", 2)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("company name exists", response.getString("message"));
    }

    @Test
    public void createCompanyNameTooLong() throws Exception {

        JSONObject body = new JSONObject();
        body.put("company_name", "a_very_very_very_very_very_very_very_very_very_very_very_very_long_company_name");
        MvcResult result = mockMvc.perform(post("/merchant/company/")
                .header("x-internal-token", 2)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("failed", response.getString("message"));
    }
}
