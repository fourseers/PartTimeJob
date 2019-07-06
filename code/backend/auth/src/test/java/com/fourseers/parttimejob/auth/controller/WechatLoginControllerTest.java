package com.fourseers.parttimejob.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.auth.entity.WechatUser;
import com.fourseers.parttimejob.auth.service.WechatUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class WechatLoginControllerTest {

    @Mock
    private Wechat wechat;

    @Mock
    private OAuth oauth;

    @Value("${app.wechat_user_prefix}")
    private String WECHAT_USER_PREFIX;

    @Value("${app.wechat_password_placeholder}")
    private String WECHAT_PASSWD_PLACEHOLDER;


    private MockMvc mockMvc;

    @Autowired
    @InjectMocks
    private WechatLoginController wechatLoginController;


    @Autowired
    private WechatUserService wechatUserService;

    @Before
    public void setUp() {
        wechat = mock(Wechat.class);
        oauth = mock(OAuth.class);
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(wechatLoginController).build();

        WechatUser wechatUser1 = new WechatUser();
        wechatUser1.setName("Anonymous");
        wechatUser1.setOpenid("fake_openid");
        wechatUser1.setCity("Shanghai");
        wechatUser1.setCountry("China");
        wechatUser1.setEducation("Master");
        wechatUser1.setGender(true);
        wechatUser1.setIdentity("310000200001010000");
        wechatUserService.save(wechatUser1);

        JSONObject wechatSuccessResponse = new JSONObject();
        wechatSuccessResponse.put("openid", "fake_openid");
        wechatSuccessResponse.put("session_key", "fake_session_key");
        when(wechat.auth("fake_appid", "fake_appsecret", "fake_token", "authorization_code")).thenReturn(wechatSuccessResponse.toJSONString());

        JSONObject wechatInvalidCodeResponse = new JSONObject();
        wechatInvalidCodeResponse.put("errorcode", 40029);
        wechatInvalidCodeResponse.put("errmsg", "invalid code, hints: [ req_id: XhbdFMwgE-6VaIaA ]");
        when(wechat.auth("fake_appid", "fake_appsecret", "invalid_token", "authorization_code")).thenReturn(wechatInvalidCodeResponse.toJSONString());

        JSONObject wechatNotExistIdResponse = new JSONObject();
        wechatNotExistIdResponse.put("openid", "no_exist_openid");
        wechatNotExistIdResponse.put("session_key", "not_exist_session_key");
        when(wechat.auth("fake_appid", "fake_appsecret", "not_exist_token", "authorization_code")).thenReturn(wechatNotExistIdResponse.toJSONString());

        JSONObject successResponse = JSON.parseObject(
                "{\n" +
                "   \"access_token\":\"MTQ0NjJkZmQ5OTM2NDE1ZTZjNGZmZjI3\",\n" +
                "   \"token_type\":\"bearer\",\n" +
                "   \"expires_in\":3600,\n" +
                "   \"refresh_token\":\"IwOGYzYTlmM2YxOTQ5MGE3YmNmMDFkNTVk\",\n" +
                "   \"scope\":\"server\"\n" +
                "}");

        when(oauth.getToken(WECHAT_USER_PREFIX + "fake_openid", WECHAT_PASSWD_PLACEHOLDER, "password", "Basic d2VjaGF0Q2xpZW50OjEyMzQ1Ng==")).thenReturn(successResponse);

//        JSONObject userNotExistResponse = JSON.parseObject(
//                "{\n" +
//                "   \"status\":400,\n" +
//                "   \"message\":\"invalid token\"\n" +
//                "}"
//        );
//        when(oauth.getToken(WECHAT_USER_PREFIX + "in", "", "password")).thenReturn(successResponse);
    }

    @Test
    public void loginSuccess() throws Exception {
        JSONObject body = new JSONObject();
        body.put("token", "fake_token");
        MvcResult result = mockMvc.perform(post("/login")
                .header(HttpHeaders.AUTHORIZATION, "Basic d2VjaGF0Q2xpZW50OjEyMzQ1Ng==")
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("200", response.getString("status"));
        assertNotNull(response.getJSONObject("data").getString("access_token"));
    }

//    @Test
//    public void loginInvalidToken() throws Exception {
//        JSONObject body = new JSONObject();
//        body.put("token", "invalid_token");
//        MvcResult result = mockMvc.perform(post("/login")
//                .content(body.toJSONString())
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().is(400))
//                .andReturn();
//
//        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
//        assertEquals("400", response.getString("status"));
//        assertEquals("invalid token", response.getString("message"));
//    }
//
//    @Test
//    public void loginNotExistToken() {
//        JSONObject body = new JSONObject();
//        body.put("token", "not_exist_token");
//        assertEquals("not exist", wechatLoginController.login(body));
//    }
//
//    @Test
//    public void registerSuccess() {
//        JSONObject body = new JSONObject();
//        body.put("token", "not_exist_token");
//        body.put("username", "some_name");
//        assertEquals("success", wechatLoginController.register(body));
//    }
//
//    @Test
//    public void registerExist() {
//        JSONObject body = new JSONObject();
//        body.put("token", "fake_token");
//        body.put("username", "some_name");
//        assertEquals("user exist", wechatLoginController.register(body));
//    }
//
//    @Test
//    public void registerInvalidToken() {
//        JSONObject body = new JSONObject();
//        body.put("token", "invalid_token");
//        body.put("name", "some_name");
//        assertEquals("invalid token", wechatLoginController.register(body));
//    }
}
