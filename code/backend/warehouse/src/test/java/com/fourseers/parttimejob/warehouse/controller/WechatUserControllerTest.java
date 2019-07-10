package com.fourseers.parttimejob.warehouse.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.warehouse.entity.Tag;
import com.fourseers.parttimejob.warehouse.entity.WechatUser;
import com.fourseers.parttimejob.warehouse.service.TagService;
import com.fourseers.parttimejob.warehouse.service.WechatUserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * WechatUserController Tester.
 *
 * @author <Authors name>
 * @since <pre>07/10/2019</pre>
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class WechatUserControllerTest {

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private TagService tagService;

    @Autowired
    private MockMvc mockMvc;

    @Value("${app.wechat_user_prefix}")
    private String WECHAT_USER_PREFIX;

    private String internalTokenString;

    @Before
    public void before() throws Exception {
        WechatUser wechatUser = new WechatUser();
        wechatUser.setName("SJH");
        wechatUser.setOpenid("FakeOpenId");
        wechatUser.setGender(true);
        wechatUser.setCountry("China");
        wechatUser.setCity("Shanghai");
        wechatUser.setEducation("Junior High School");
        wechatUser.setIdentity("310110123412341234");
        wechatUser.setPhone("13812345678");

        List<Tag> tags = new ArrayList<>();
        List<String> tagList = Arrays.asList("后厨打杂", "餐厅服务员", "前台服务员", "全天", "半天");
        for(String tagName: tagList) {
            Tag tag = new Tag();
            tag.setName(tagName);
            tags.add(tag);
            tagService.addOne(tag);
        }

        Set<Tag> userTags = new HashSet<Tag>();
        userTags.add(tags.get(0));
        userTags.add(tags.get(3));
        wechatUser.setTags(userTags);
        wechatUserService.save(wechatUser);

        internalTokenString = WECHAT_USER_PREFIX + "FakeOpenId";
    }

    @After
    public void after() throws Exception {
    }

    /**
     *
     * Method: getUserInfo(@RequestHeader("x-internal-token") String internalToken)
     *
     */
    @Test
    public void testGetUserInfoSuccess() throws Exception {

        MvcResult result = mockMvc.perform(get("/user/info")
                .header("x-internal-token", internalTokenString))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject resp = JSON.parseObject(result.getResponse().getContentAsString());
        JSONObject data = resp.getJSONObject("data");
        assertEquals(resp.getInteger("status"), Integer.valueOf(200));
        assertNotNull(data);
        JSONObject info = data.getJSONObject("info");
        assertNotNull(info);
        assertEquals(info.getString("name"), "SJH");
        assertEquals(info.getBoolean("gender"), true);
        assertEquals(info.getString("phone"), "13812345678");
        assertEquals(info.getString("education"), "Junior High School");
        assertNull(info.getString("openid"));
    }

    @Test
    public void testGetUserInfoFailWrongToken() throws Exception {
        MvcResult result = mockMvc.perform(get("/user/info")
                .header("x-internal-token", WECHAT_USER_PREFIX + "wrongToken"))
                .andExpect(status().is4xxClientError())
                .andReturn();

        JSONObject resp = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals(resp.getInteger("status"), Integer.valueOf(400));
    }

    @Test
    public void testGetUserInfoFailWrongTokenFormat() throws Exception {

        MvcResult result = mockMvc.perform(get("/user/info")
                .header("x-internal-token", internalTokenString.substring(1)))
                .andExpect(status().is4xxClientError())
                .andReturn();

        JSONObject resp = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals(resp.getInteger("status"), Integer.valueOf(400));
    }

    /**
     *
     * Method: updateUserInfo(@RequestBody WechatUserInfoDto userInfo, @RequestHeader("x-internal-token") String internalToken)
     *
     */
    @Test
    public void testUpdateUserInfo() throws Exception {
        JSONObject request = new JSONObject();
        request.fluentPut("name", "ZJY");
        request.fluentPut("gender", false);
        request.fluentPut("phone", "13887654321");
        request.fluentPut("education", "college");
        mockMvc.perform(
                post("/user/info")
                .header("x-internal-token", internalTokenString)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString())
        )
                .andExpect(status().isOk())
                .andReturn();

        MvcResult result = mockMvc.perform(get("/user/info")
                .header("x-internal-token", internalTokenString))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject resp = JSON.parseObject(result.getResponse().getContentAsString());
        JSONObject data = resp.getJSONObject("data");
        assertEquals(resp.getInteger("status"), Integer.valueOf(200));
        assertNotNull(data);
        JSONObject info = data.getJSONObject("info");
        assertNotNull(info);
        assertEquals(info.getString("name"), "ZJY");
        assertEquals(info.getBoolean("gender"), false);
        assertEquals(info.getString("phone"), "13887654321");
        assertEquals(info.getString("education"), "college");
        assertNull(info.getString("openid"));
    }

    @Test
    public void testAddTags() throws Exception {
        JSONObject request = new JSONObject();
        JSONArray tagList = new JSONArray();
        tagList.add(1); tagList.add(2);
        request.fluentPut("tags", tagList);
        mockMvc.perform(
                post("/user/info")
                        .header("x-internal-token", internalTokenString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult result = mockMvc.perform(get("/user/info")
                .header("x-internal-token", internalTokenString))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject resp = JSON.parseObject(result.getResponse().getContentAsString());
        JSONObject data = resp.getJSONObject("data");
        assertEquals(resp.getInteger("status"), Integer.valueOf(200));
        assertNotNull(data);
        JSONObject info = data.getJSONObject("info");
        assertNotNull(info);
        JSONArray gottenTags = info.getJSONArray("tags");
        assertNotNull(gottenTags);
        assertEquals(gottenTags.size(), 2);

        // remove tag
        tagList.remove(1);
        request = new JSONObject();
        request.fluentPut("tags", tagList);
        mockMvc.perform(
                post("/user/info")
                        .header("x-internal-token", internalTokenString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult result2 = mockMvc.perform(get("/user/info")
                .header("x-internal-token", internalTokenString))
                .andExpect(status().isOk())
                .andReturn();

        resp = JSON.parseObject(result2.getResponse().getContentAsString());
        data = resp.getJSONObject("data");
        assertEquals(resp.getInteger("status"), Integer.valueOf(200));
        assertNotNull(data);
        info = data.getJSONObject("info");
        assertNotNull(info);
        gottenTags = info.getJSONArray("tags");
        assertNotNull(gottenTags);
        assertEquals(gottenTags.size(), 1);
    }

    @Test
    public void testAddInvalidTags() throws Exception {
        JSONObject request = new JSONObject();
        JSONArray tagList = new JSONArray();
        tagList.add(1); tagList.add(10); // invalid tag 10
        request.fluentPut("tags", tagList);
        mockMvc.perform(
                post("/user/info")
                        .header("x-internal-token", internalTokenString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.toString()))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testUpdateUserInfoFailWrongToken() throws Exception {
        JSONObject request = new JSONObject();
        request.fluentPut("name", "CYJ");
        MvcResult result = mockMvc.perform(post("/user/info")
                .header("x-internal-token", WECHAT_USER_PREFIX + "wrongToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString()))
                .andExpect(status().is4xxClientError())
                .andReturn();

        JSONObject resp = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals(resp.getInteger("status"), Integer.valueOf(400));
    }

} 
