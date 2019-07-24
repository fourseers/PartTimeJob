package com.fourseers.parttimejob.warehouse.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.common.entity.Etc;
import com.fourseers.parttimejob.common.entity.Tag;
import com.fourseers.parttimejob.warehouse.service.TagService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class InfoControllerTest {

    @Autowired
    private TagService tagService;

    @Autowired
    private MockMvc mockMvc;

    private static List<Etc.Education> educationList = InfoController.educationList;
    private static List<String> tags = Arrays.asList("服务员", "厨房后勤", "全天", "半天");


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetInfoList() throws Exception {

        for(String tagName: tags) {
            Tag tag = new Tag();
            tag.setName(tagName);
            tagService.addOne(tag);
        }

        MvcResult result = mockMvc.perform(get("/user/register-info"))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject resp = JSON.parseObject(result.getResponse().getContentAsString());
        assertNotNull(resp.getJSONObject("data"));
        JSONArray respEducation = resp.getJSONObject("data").getJSONArray("education");
        JSONArray respTags = resp.getJSONObject("data").getJSONArray("tags");
        assertEquals(respEducation.size(), 6);
        assertEquals(respTags.size(), 4);
    }

    @Test
    public void testGetTagList() throws Exception {

        for(String tagName: tags) {
            Tag tag = new Tag();
            tag.setName(tagName);
            tagService.addOne(tag);
        }

        MvcResult result = mockMvc.perform(get("/merchant/tags"))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject resp = JSON.parseObject(result.getResponse().getContentAsString());
        assertNotNull(resp.getJSONArray("data"));
        JSONArray respTags = resp.getJSONArray("data");
        assertEquals(respTags.size(), 4);
    }
}
