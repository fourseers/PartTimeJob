package com.fourseers.parttimejob.arrangement.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.arrangement.repository.WorkRepository;
import com.fourseers.parttimejob.common.entity.Work;
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
public class WorkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WorkRepository workRepository;

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

    @Test
    public void remarkWorkSuccess() throws Exception {
        String bossname = "Tim Cook";

        JSONObject body = new JSONObject();
        body.fluentPut("work_id", 1)
            .fluentPut("score", 5);

        MvcResult result = mockMvc.perform(post("/merchant/work/remark")
                .header("x-internal-token", bossname)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));

        Work work = workRepository.findByWorkId(1);
        assertEquals(Integer.valueOf(5), work.getScore());
    }

    @Test
    public void remarkWorkAlreadyRemarked() throws Exception {
        String bossname = "Tim Cook";

        JSONObject body = new JSONObject();
        body.fluentPut("work_id", 2)
                .fluentPut("score", 5);

        MvcResult result = mockMvc.perform(post("/merchant/work/remark")
                .header("x-internal-token", bossname)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("work already remarked", response.getString("message"));
    }

    @Test
    public void remarkWorkNotBelongTo() throws Exception {
        String bossname = "罗永浩";

        JSONObject body = new JSONObject();
        body.fluentPut("work_id", 1)
                .fluentPut("score", 5);

        MvcResult result = mockMvc.perform(post("/merchant/work/remark")
                .header("x-internal-token", bossname)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("work not exist or not managed by current user", response.getString("message"));
    }

    @Test
    public void remarkWorkNotExist() throws Exception {
        String bossname = "Tim Cook";

        JSONObject body = new JSONObject();
        body.fluentPut("work_id", 666)
                .fluentPut("score", 5);

        MvcResult result = mockMvc.perform(post("/merchant/work/remark")
                .header("x-internal-token", bossname)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("work not exist or not managed by current user", response.getString("message"));
    }

    @Test
    public void remarkWorkNoCompany() throws Exception {
        String bossname = "poor user";

        JSONObject body = new JSONObject();
        body.fluentPut("work_id", 1)
                .fluentPut("score", 5);

        MvcResult result = mockMvc.perform(post("/merchant/work/remark")
                .header("x-internal-token", bossname)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("user does not belong to a company", response.getString("message"));
    }
}
