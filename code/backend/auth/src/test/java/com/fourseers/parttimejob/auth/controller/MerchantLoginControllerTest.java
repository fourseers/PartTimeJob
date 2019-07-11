package com.fourseers.parttimejob.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.auth.entity.MerchantUser;
import com.fourseers.parttimejob.auth.service.MerchantUserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MerchantLoginControllerTest {

    @Mock
    private static OAuth oAuth;

    private static MockMvc mockMvc;

    @Autowired
    @InjectMocks
    private MerchantLoginController merchantLoginController;

    @Autowired
    private MerchantUserService merchantUserService;

    private static final String DEFAULT_USERNAME = "bigBoss001";
    private static final String DEFAULT_PASSWD = "bigBossHasASecret";

    private static String basicAuthString = "Basic" + Base64Utils.encodeToString("webClient:123456".getBytes());

    @Before
    public void setUp() {
        oAuth = mock(OAuth.class);
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(merchantLoginController).build();
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setUsername(DEFAULT_USERNAME);
        merchantUser.setPassword(DEFAULT_PASSWD);
        merchantUserService.save(merchantUser);

        JSONObject successfulOAuthResponse = new JSONObject()
                .fluentPut("access_token","9c37b266-a876-476d-8a2a-055572318665")
                .fluentPut("refresh_token","17c8093d-16b0-422d-9511-cb38f09c2693")
                .fluentPut("scope","merchant")
                .fluentPut("token_type","bearer")
                .fluentPut("expires_in",43199);
        JSONObject errorOAuthResponse = new JSONObject()
                .fluentPut("status", "401")
                .fluentPut("error", "Unauthorized")
                .fluentPut("message", "Unauthorized")
                .fluentPut("path", "/oauth/token")
                .fluentPut("timestamp", new Date().toString());
        JSONObject invalidGrantResponse = new JSONObject()
                .fluentPut("status", "401")
                .fluentPut("message", "Bad request")
                .fluentPut("data", new JSONObject()
                        .fluentPut("error", "invalid_grant")
                        .fluentPut("error_description", "用户名或密码错误"));
        when(oAuth.getToken(DEFAULT_USERNAME, DEFAULT_PASSWD, "password", basicAuthString))
                .thenReturn(successfulOAuthResponse);
        when(oAuth.getToken(not(eq(DEFAULT_USERNAME)), anyString(), notNull(), eq(basicAuthString)))
                .thenReturn(errorOAuthResponse);
        when(oAuth.getToken(eq(DEFAULT_USERNAME), not(eq(DEFAULT_PASSWD)), eq("password"), eq(basicAuthString)))
                .thenReturn(invalidGrantResponse);
        when(oAuth.getToken(eq(DEFAULT_USERNAME), eq(DEFAULT_PASSWD), eq("password"), not(eq(basicAuthString))))
                .thenReturn(errorOAuthResponse);
        when(oAuth.getToken(isNull(), anyString(), anyString(), eq(basicAuthString)))
                .thenReturn(errorOAuthResponse);
    }

    @After
    public void after() throws Exception {
    }


    @Test
    public void testMerchantRegisterSuccess() throws Exception {
        JSONObject loginRequest = new JSONObject();
        loginRequest.fluentPut("username", "bigBoss002")
                .fluentPut("password", "bigBossSecret");
        MvcResult result = mockMvc.perform(
                post("/merchant/register")
                        .header(HttpHeaders.AUTHORIZATION, basicAuthString)
                        .content(loginRequest.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // check if entry exists
        assertNotNull(merchantUserService.findByUsername("bigBoss002"));
    }

    @Test
    public void testMerchantRegisterUserExists() throws Exception {
        JSONObject loginRequest = new JSONObject();
        loginRequest.fluentPut("username", DEFAULT_USERNAME)
                .fluentPut("password", DEFAULT_PASSWD);
        mockMvc.perform(
                post("/merchant/register")
                        .header(HttpHeaders.AUTHORIZATION, basicAuthString)
                        .content(loginRequest.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testMerchantRegisterFail() throws Exception {
        JSONObject loginRequest = new JSONObject();
        loginRequest.fluentPut("username", "")
                .fluentPut("password", DEFAULT_PASSWD);
        MvcResult result = mockMvc.perform(
                post("/merchant/register")
                        .header(HttpHeaders.AUTHORIZATION, basicAuthString)
                        .content(loginRequest.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
//        assertEquals("400", response.getString("status"));
        assertEquals(null, response.getString("data"));
    }

    @Test
    public void testMerchantLoginSuccess() throws Exception {
        JSONObject loginRequest = new JSONObject();
        loginRequest.fluentPut("username", DEFAULT_USERNAME)
                    .fluentPut("password", DEFAULT_PASSWD);
        MvcResult result = mockMvc.perform(
                post("/merchant/login")
                .header(HttpHeaders.AUTHORIZATION, basicAuthString)
                .content(loginRequest.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("200", response.getString("status"));
        assertNotNull(response.getJSONObject("data").getString("access_token"));
    }

    @Test
    public void testMerchantLoginFail() throws Exception {
        JSONObject loginRequest = new JSONObject();
        loginRequest.fluentPut("username", DEFAULT_USERNAME)
                .fluentPut("password", "someWrongPassword");
        MvcResult result = mockMvc.perform(
                post("/merchant/login")
                        .header(HttpHeaders.AUTHORIZATION, basicAuthString)
                        .content(loginRequest.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("401", response.getString("status"));
    }

} 
