package com.fourseers.parttimejob.warehouse.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.common.entity.Company;
import com.fourseers.parttimejob.common.entity.MerchantUser;
import com.fourseers.parttimejob.warehouse.projection.MerchantUserInfoProjection;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    @Test
    public void getOnePageUsersSuccess() throws Exception {
        String admin = "God";

        MerchantUser merchantUser1 = new MerchantUser();
        merchantUser1.setUsername("Tim Cook");
        merchantUser1.setPassword("some password");
        merchantUserService.save(merchantUser1);

        MerchantUser merchantUser2 = new MerchantUser();
        merchantUser2.setUsername("罗永浩");
        merchantUser2.setPassword("some password");
        merchantUserService.save(merchantUser2);

        MvcResult result = mockMvc.perform(get("/admin/merchant-users")
                .header("x-internal-token", admin)
                .param("page_count", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertNotNull(response.getJSONObject("data").getJSONArray("content"));
        assertEquals(2, response.getJSONObject("data").getJSONArray("content").size());
        assertEquals("success", response.getString("message"));
    }

    @Test
    public void getUsersFromLastPageSuccess() throws Exception {
        String admin = "God";

        for (int i = 0; i < 95; i++) {
            MerchantUser merchantUser = new MerchantUser();
            merchantUser.setUsername("孙悟空 " + i);
            merchantUser.setPassword("some password");
            merchantUserService.save(merchantUser);
        }

        MvcResult result = mockMvc.perform(get("/admin/merchant-users")
                .header("x-internal-token", admin)
                .param("page_count", "9")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertNotNull(response.getJSONObject("data").getJSONArray("content"));
        assertEquals(5, response.getJSONObject("data").getJSONArray("content").size());
        for (int i = 0; i < 5; i++) {
            assertEquals("孙悟空 9" + i, response.getJSONObject("data").getJSONArray("content").getJSONObject(i).getString("username"));
        }
        assertEquals("success", response.getString("message"));
    }

    @Test
    public void getOnePageUsersNotExist() throws Exception {
        String admin = "God";

        MvcResult result = mockMvc.perform(get("/admin/merchant-users")
                .header("x-internal-token", admin)
                .param("page_count", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertNotNull(response.getJSONObject("data").getJSONArray("content"));
        assertEquals(0, response.getJSONObject("data").getJSONArray("content").size());
        assertEquals("success", response.getString("message"));
    }

    @Test
    public void banUserSuccess() throws Exception {
        String admin = "God";

        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setUsername("Evil");
        merchantUser.setPassword("some password");
        merchantUserService.save(merchantUser);

        Integer userId = merchantUser.getUserId();

        MvcResult result = mockMvc.perform(put("/admin/merchant-user/ban")
                .header("x-internal-token", admin)
                .param("user_id", String.valueOf(userId))
                .param("ban", "true")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));
        MerchantUserInfoProjection updatedMerchantUser = merchantUserService.findBriefByUserId(userId);
        assertEquals(true, updatedMerchantUser.getBanned());
    }

    @Test
    public void unbanUserSuccess() throws Exception {
        String admin = "God";

        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setUsername("Not evil");
        merchantUser.setPassword("some password");
        merchantUser.setBanned(true);
        merchantUserService.save(merchantUser);

        Integer userId = merchantUser.getUserId();

        MvcResult result = mockMvc.perform(put("/admin/merchant-user/ban")
                .header("x-internal-token", admin)
                .param("user_id", String.valueOf(userId))
                .param("ban", "false")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));
        MerchantUserInfoProjection updatedMerchantUser = merchantUserService.findBriefByUserId(userId);
        assertEquals(false, updatedMerchantUser.getBanned());
    }

    @Test
    public void banUserNotExist() throws Exception {
        String admin = "God";

        MvcResult result = mockMvc.perform(put("/admin/merchant-user/ban")
                .header("x-internal-token", admin)
                .param("user_id", "666")
                .param("ban", "true")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("user not exist", response.getString("message"));
    }
}
