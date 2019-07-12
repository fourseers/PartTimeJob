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
import org.springframework.util.Base64Utils;

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

    private static String clientAuthString = "Basic " + Base64Utils.encodeToString("wechatClient:123456".getBytes());

    @Before
    public void setUp() {
        wechat = mock(Wechat.class);
        oauth = mock(OAuth.class);
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(wechatLoginController).build();

        WechatUser wechatUser1 = new WechatUser();
        wechatUser1.setName("Anonymous");
        wechatUser1.setOpenid("correct_openid");
        wechatUser1.setCity("Shanghai");
        wechatUser1.setCountry("China");
        wechatUser1.setEducation("Master");
        wechatUser1.setGender(true);
        wechatUser1.setIdentity("310000200001010000");
        wechatUserService.save(wechatUser1);
    }

    @Test
    public void loginSuccess() throws Exception {

        JSONObject wechatResponse = new JSONObject();
        wechatResponse.put("openid", "correct_openid");
        wechatResponse.put("session_key", "correct_session_key");
        when(wechat.auth("fake_appid", "fake_appsecret", "correct_code", "authorization_code")).thenReturn(wechatResponse.toJSONString());

        JSONObject oauthResponse = new JSONObject();
        oauthResponse.put("access_token", "MTQ0NjJkZmQ5OTM2NDE1ZTZjNGZmZjI3");
        oauthResponse.put("token_type", "bearer");
        oauthResponse.put("expire_in", 3600);
        oauthResponse.put("refresh_token", "IwOGYzYTlmM2YxOTQ5MGE3YmNmMDFkNTVk");
        oauthResponse.put("scope", "server");
        when(oauth.getToken(WECHAT_USER_PREFIX + "correct_openid", WECHAT_PASSWD_PLACEHOLDER, "password", clientAuthString)).thenReturn(oauthResponse);

        JSONObject body = new JSONObject();
        body.put("token", "correct_code");
        MvcResult result = mockMvc.perform(post("/wechat/login")
                .header(HttpHeaders.AUTHORIZATION, clientAuthString)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("200", response.getString("status"));
        assertNotNull(response.getJSONObject("data").getString("access_token"));
    }

    @Test
    public void loginClientAuthFailure() throws Exception {

        JSONObject wechatResponse = new JSONObject();
        wechatResponse.put("openid", "correct_openid");
        wechatResponse.put("session_key", "correct_session_key");
        when(wechat.auth("fake_appid", "fake_appsecret", "correct_code", "authorization_code")).thenReturn(wechatResponse.toJSONString());

        JSONObject oauthResponse = new JSONObject();
        oauthResponse.put("access_token", "MTQ0NjJkZmQ5OTM2NDE1ZTZjNGZmZjI3");
        oauthResponse.put("token_type", "bearer");
        oauthResponse.put("expire_in", 3600);
        oauthResponse.put("refresh_token", "IwOGYzYTlmM2YxOTQ5MGE3YmNmMDFkNTVk");
        oauthResponse.put("scope", "server");
        when(oauth.getToken(WECHAT_USER_PREFIX + "correct_openid", WECHAT_PASSWD_PLACEHOLDER, "password", clientAuthString + " ")).thenReturn(oauthResponse);

        JSONObject body = new JSONObject();
        body.put("token", "correct_code");
        MvcResult result = mockMvc.perform(post("/wechat/login")
                .header(HttpHeaders.AUTHORIZATION, clientAuthString)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("400", response.getString("status"));
        assertEquals("unhandled error", response.getString("message"));
    }

    @Test
    public void loginInvalidCode() throws Exception {

        JSONObject wechatResponse = new JSONObject();
        wechatResponse.put("errorcode", 40029);
        wechatResponse.put("errmsg", "invalid code, hints: [ req_id: XhbdFMwgE-6VaIaA ]");
        when(wechat.auth("fake_appid", "fake_appsecret", "invalid_code", "authorization_code")).thenReturn(wechatResponse.toJSONString());

        JSONObject body = new JSONObject();
        body.put("token", "invalid_code");
        MvcResult result = mockMvc.perform(post("/wechat/login")
                .header(HttpHeaders.AUTHORIZATION, clientAuthString)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("400", response.getString("status"));
        assertEquals("invalid code", response.getString("message"));
    }

    @Test
    public void loginNewUser() throws Exception {

        JSONObject wechatResponse = new JSONObject();
        wechatResponse.put("openid", "new_user_openid");
        wechatResponse.put("session_key", "new_user_session_key");
        when(wechat.auth("fake_appid", "fake_appsecret", "new_user_code", "authorization_code")).thenReturn(wechatResponse.toJSONString());

        JSONObject body = new JSONObject();
        body.put("token", "new_user_code");
        MvcResult result = mockMvc.perform(post("/wechat/login")
                .header(HttpHeaders.AUTHORIZATION, clientAuthString)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("400", response.getString("status"));
        assertEquals("user not exist", response.getString("message"));
    }

    @Test
    public void registerSuccess() throws Exception {

        JSONObject wechatResponse = new JSONObject();
        wechatResponse.put("openid", "new_user_openid");
        wechatResponse.put("session_key", "new_user_session_key");
        when(wechat.auth("fake_appid", "fake_appsecret", "new_user_code", "authorization_code")).thenReturn(wechatResponse.toJSONString());

        JSONObject oauthResponse = new JSONObject();
        oauthResponse.put("access_token", "MTQ0NjJkZmQ5OTM2NDE1ZTZjNGZmZjI3");
        oauthResponse.put("token_type", "bearer");
        oauthResponse.put("expire_in", 3600);
        oauthResponse.put("refresh_token", "IwOGYzYTlmM2YxOTQ5MGE3YmNmMDFkNTVk");
        oauthResponse.put("scope", "server");
        when(oauth.getToken(WECHAT_USER_PREFIX + "new_user_openid", WECHAT_PASSWD_PLACEHOLDER, "password", clientAuthString)).thenReturn(oauthResponse);


        JSONObject body = new JSONObject();
        body.put("token", "new_user_code");
        body.put("name", "Trump");
        body.put("city", "Washington");
        body.put("country", "America");
        body.put("education", "Primary School");
        body.put("gender", true);
        body.put("identity", "330000200001010000");

        MvcResult result = mockMvc.perform(post("/wechat/register")
                .header(HttpHeaders.AUTHORIZATION, clientAuthString)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("200", response.getString("status"));
        assertNotNull(response.getJSONObject("data").getString("access_token"));
    }

    @Test
    public void registerClientAuthFailure() throws Exception {

        JSONObject wechatResponse = new JSONObject();
        wechatResponse.put("openid", "new_user_openid");
        wechatResponse.put("session_key", "new_user_session_key");
        when(wechat.auth("fake_appid", "fake_appsecret", "new_user_code", "authorization_code")).thenReturn(wechatResponse.toJSONString());

        JSONObject oauthResponse = new JSONObject();
        oauthResponse.put("access_token", "MTQ0NjJkZmQ5OTM2NDE1ZTZjNGZmZjI3");
        oauthResponse.put("token_type", "bearer");
        oauthResponse.put("expire_in", 3600);
        oauthResponse.put("refresh_token", "IwOGYzYTlmM2YxOTQ5MGE3YmNmMDFkNTVk");
        oauthResponse.put("scope", "server");
        when(oauth.getToken(WECHAT_USER_PREFIX + "new_user_openid", WECHAT_PASSWD_PLACEHOLDER, "password", clientAuthString + " ")).thenReturn(oauthResponse);


        JSONObject body = new JSONObject();
        body.put("token", "new_user_code");
        body.put("name", "Trump");
        body.put("city", "Washington");
        body.put("country", "America");
        body.put("education", "Primary School");
        body.put("gender", true);
        body.put("identity", "330000200001010000");

        MvcResult result = mockMvc.perform(post("/wechat/register")
                .header(HttpHeaders.AUTHORIZATION, clientAuthString)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("400", response.getString("status"));
        assertEquals("unhandled error", response.getString("message"));
    }

    @Test
    public void registerExist() throws Exception {

        JSONObject wechatResponse = new JSONObject();
        wechatResponse.put("openid", "correct_openid");
        wechatResponse.put("session_key", "correct_session_key");
        when(wechat.auth("fake_appid", "fake_appsecret", "correct_code", "authorization_code")).thenReturn(wechatResponse.toJSONString());

        JSONObject body = new JSONObject();
        body.put("token", "correct_code");
        body.put("name", "Trump");
        body.put("city", "Washington");
        body.put("country", "America");
        body.put("education", "Primary School");
        body.put("gender", true);
        body.put("identity", "330000200001010000");

        MvcResult result = mockMvc.perform(post("/wechat/register")
                .header(HttpHeaders.AUTHORIZATION, clientAuthString)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("400", response.getString("status"));
        assertEquals("user exist", response.getString("message"));
    }

    @Test
    public void registerInvalidCode() throws Exception {

        JSONObject wechatResponse = new JSONObject();
        wechatResponse.put("errorcode", 40029);
        wechatResponse.put("errmsg", "invalid code, hints: [ req_id: XhbdFMwgE-6VaIaA ]");
        when(wechat.auth("fake_appid", "fake_appsecret", "invalid_code", "authorization_code")).thenReturn(wechatResponse.toJSONString());

        JSONObject body = new JSONObject();
        body.put("token", "invalid_code");
        body.put("name", "Trump");
        body.put("city", "Washington");
        body.put("country", "America");
        body.put("education", "Primary School");
        body.put("gender", true);
        body.put("identity", "330000200001010000");

        MvcResult result = mockMvc.perform(post("/wechat/register")
                .header(HttpHeaders.AUTHORIZATION, clientAuthString)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("400", response.getString("status"));
        assertEquals("invalid code", response.getString("message"));
    }
}
