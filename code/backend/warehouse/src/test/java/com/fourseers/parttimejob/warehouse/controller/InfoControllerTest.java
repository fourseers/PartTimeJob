package com.fourseers.parttimejob.warehouse.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
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

    private static List<String> educationList = Arrays.asList(
            "初中以下", "初中毕业", "高中毕业", "本科毕业", "研究生及以上");

    private static List<String> tags = Arrays.asList("服务员", "厨房后勤", "全天", "半天");


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetInfoList() throws Exception {

        List<Tag> tagEntites = new ArrayList<>();
        for(String tagName: tags) {
            Tag tag = new Tag();
            tag.setName(tagName);
            tagService.addOne(tag);
            tagEntites.add(tag);
        }
        JSONObject expectedResp = new JSONObject();
        expectedResp.put("education",
                new JSONArray().fluentAddAll(educationList));
        expectedResp.put("tags",
                new JSONArray().fluentAddAll(tagEntites));

        MvcResult result = mockMvc.perform(get("/user/register-info"))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject resp = JSON.parseObject(result.getResponse().getContentAsString());
        JSONArray respEducation = expectedResp.getJSONArray("education");
        JSONArray respTags = expectedResp.getJSONArray("tags");
        assertEquals(respEducation.size(), 5);
        assertEquals(respTags.size(), 4);
    }
}