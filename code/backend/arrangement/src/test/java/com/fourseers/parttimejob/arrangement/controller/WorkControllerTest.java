package com.fourseers.parttimejob.arrangement.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
public class WorkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getOnePageWorksByCompanySuccess() throws Exception {
        String bossname = "Tim Cook";


        MvcResult result = mockMvc.perform(get("/merchant/works")
                .header("x-internal-token", bossname)
                .param("page_count", "0"))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));
        assertNotNull(response.getJSONObject("data"));
        assertNotNull(response.getJSONObject("data").getJSONArray("content"));
        assertEquals(2, response.getJSONObject("data").getJSONArray("content").size());
    }

    @Test
    public void getOnePageWorksByCompanyNoExist() throws Exception {
        String bossname = "罗永浩";


        MvcResult result = mockMvc.perform(get("/merchant/works")
                .header("x-internal-token", bossname)
                .param("page_count", "0"))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("work not exist", response.getString("message"));
    }

    @Test
    public void getOnePageWorksByCompanyPageNegative() throws Exception {
        String bossname = "Tim Cook";


        MvcResult result = mockMvc.perform(get("/merchant/works")
                .header("x-internal-token", bossname)
                .param("page_count", "-1"))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("incorrect param", response.getString("message"));
    }

    @Test
    public void getOnePageWorksByCompanyPageOverflow() throws Exception {
        String bossname = "Tim Cook";


        MvcResult result = mockMvc.perform(get("/merchant/works")
                .header("x-internal-token", bossname)
                .param("page_count", "666"))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("work not exist", response.getString("message"));
    }

    @Test
    public void getOnePageWorksByShopSuccess() throws Exception {
        String bossname = "Tim Cook";


        MvcResult result = mockMvc.perform(get("/merchant/works")
                .header("x-internal-token", bossname)
                .param("shop_id", "2")
                .param("page_count", "0"))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));
        assertNotNull(response.getJSONObject("data"));
        assertNotNull(response.getJSONObject("data").getJSONArray("content"));
        assertEquals(1, response.getJSONObject("data").getJSONArray("content").size());
    }

    @Test
    public void getOnePageWorksByShopAndWorkNoExist() throws Exception {
        String bossname = "罗永浩";


        MvcResult result = mockMvc.perform(get("/merchant/works")
                .header("x-internal-token", bossname)
                .param("shop_id", "3")
                .param("page_count", "0"))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("work not exist", response.getString("message"));
    }

    @Test
    public void getOnePageWorksByShopAndShopNoExist() throws Exception {
        String bossname = "罗永浩";


        MvcResult result = mockMvc.perform(get("/merchant/works")
                .header("x-internal-token", bossname)
                .param("shop_id", "666")
                .param("page_count", "0"))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("shop not exist or not belong to", response.getString("message"));
    }

    @Test
    public void getOnePageWorksByShopAndShopNotBelongTo() throws Exception {
        String bossname = "罗永浩";


        MvcResult result = mockMvc.perform(get("/merchant/works")
                .header("x-internal-token", bossname)
                .param("shop_id", "2")
                .param("page_count", "0"))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("shop not exist or not belong to", response.getString("message"));
    }

    @Test
    public void getOnePageWorksByShopPageNegative() throws Exception {
        String bossname = "Tim Cook";


        MvcResult result = mockMvc.perform(get("/merchant/works")
                .header("x-internal-token", bossname)
                .param("shop_id", "2")
                .param("page_count", "-1"))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("incorrect param", response.getString("message"));
    }

    @Test
    public void getOnePageWorksByShopPageOverflow() throws Exception {
        String bossname = "Tim Cook";


        MvcResult result = mockMvc.perform(get("/merchant/works")
                .header("x-internal-token", bossname)
                .param("shop_id", "2")
                .param("page_count", "666"))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("work not exist", response.getString("message"));
    }
}
