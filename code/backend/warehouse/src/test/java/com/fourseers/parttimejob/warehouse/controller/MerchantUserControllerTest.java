package com.fourseers.parttimejob.warehouse.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.common.entity.Company;
import com.fourseers.parttimejob.common.entity.MerchantUser;
import com.fourseers.parttimejob.warehouse.service.CompanyService;
import com.fourseers.parttimejob.warehouse.service.MerchantUserService;
import com.fourseers.parttimejob.warehouse.service.ShopService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MerchantUserControllerTest {

    @Autowired
    ShopService shopService;

    @Autowired
    CompanyService companyService;

    @Autowired
    MerchantUserService merchantUserService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getUserSuccess() throws Exception {
        String username = "Tim Cook";
        String admin = "God";

        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setUsername(username);
        merchantUser.setPassword("some password");
        merchantUserService.save(merchantUser);
        Company company = new Company();
        company.setCompanyName("Apple");
        companyService.save(company, merchantUser.getUserId());
        merchantUser.setCompany(company);
        merchantUserService.save(merchantUser);
        Integer userId = merchantUser.getUserId();

        MvcResult result = mockMvc.perform(get("/admin/merchant-user")
                .header("x-internal-token", admin)
                .param("user_id", String.valueOf(userId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertNotNull(response.getJSONObject("data"));
        assertEquals(username, response.getJSONObject("data").getString("username"));
        assertEquals("Apple", response.getJSONObject("data").getString("company_name"));
        assertEquals("success", response.getString("message"));
    }

    @Test
    public void getUserNotExist() throws Exception {
        String username = "Tim Cook";
        String admin = "God";

        MvcResult result = mockMvc.perform(get("/admin/merchant-user")
                .header("x-internal-token", admin)
                .param("user_id", "666")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("user not exist", response.getString("message"));
    }
}
