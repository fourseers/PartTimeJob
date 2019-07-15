package com.fourseers.parttimejob.warehouse.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.common.entity.Industry;
import com.fourseers.parttimejob.warehouse.service.IndustryService;
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
public class IndustryControllerTest {

    @Autowired
    private IndustryService industryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllIndustriesNotNull() throws Exception {

        String username = "Tim Cook";
        Industry industry1 = new Industry();
        industry1.setIndustryName("IT");
        industryService.save(industry1);

        Industry industry2 = new Industry();
        industry2.setIndustryName("TI");
        industryService.save(industry2);

        MvcResult result = mockMvc.perform(get("/merchant/industry")
                .header("x-internal-token", username))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));
        assertNotNull(response.getJSONArray("data"));
        assertEquals(2, response.getJSONArray("data").size());
    }

    @Test
    public void getAllIndustriesNull() throws Exception {

        String username = "Tim Cook";

        MvcResult result = mockMvc.perform(get("/merchant/industry")
                .header("x-internal-token", username))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));
        assertNotNull(response.getJSONArray("data"));
        assertEquals(0, response.getJSONArray("data").size());
    }
}
