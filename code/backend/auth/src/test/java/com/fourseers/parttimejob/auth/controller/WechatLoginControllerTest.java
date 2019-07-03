package com.fourseers.parttimejob.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.auth.entity.WechatUser;
import com.fourseers.parttimejob.auth.service.WechatUserService;
import feign.Feign;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import feign.gson.GsonDecoder;
import feign.mock.HttpMethod;
import feign.mock.MockClient;
import feign.mock.MockTarget;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.lang.reflect.Type;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class WechatLoginControllerTest {

    private MockClient mockClient;
    private Wechat wechat;

    @Autowired
    WechatLoginController wechatLoginController;

    @Autowired
    WechatUserService wechatUserService;

    class AssertionDecoder implements Decoder {

        private final Decoder delegate;

        public AssertionDecoder(Decoder delegate) {
            this.delegate = delegate;
        }

        @Override
        public Object decode(Response response, Type type)
                throws IOException, DecodeException, FeignException {
            assertThat(response.request(), notNullValue());

            return delegate.decode(response, type);
        }

    }

    @Before
    public void setup() {
        mockClient = new MockClient();
        byte[] successData = "{openid: \"fake_openid\", session_key: \"fake_session_key\"}".getBytes();
        byte[] invalidCodeData = "{\"errcode\":40029,\"errmsg\":\"invalid code, hints: [ req_id: XhbdFMwgE-6VaIaA ]\"}".getBytes();
        byte[] notExistIdData = "{openid: \"no_exist_openid\", session_key: \"not_exist_session_key\"}".getBytes();
        wechat = Feign.builder().decoder(new AssertionDecoder(new GsonDecoder()))
                .client(mockClient
                        .ok(HttpMethod.GET, "/sns/jscode2session?appid=fake_appid&secret=fake_appsecret&js_code=fake_token&grant_type=authorization_code", successData)
                        .ok(HttpMethod.GET, "/sns/jscode2session?appid=fake_appid&secret=fake_appsecret&js_code=invalid_token&grant_type=authorization_code", invalidCodeData)
                        .ok(HttpMethod.GET, "/sns/jscode2session?appid=fake_appid&secret=fake_appsecret&js_code=not_exist_token&grant_type=authorization_code", notExistIdData))
                .target(new MockTarget<>(Wechat.class));
        wechatLoginController.setWechat(wechat);
        WechatUser wechatUser1 = new WechatUser();
        wechatUser1.setName("Anonymous");
        wechatUser1.setOpenid("fake_openid");
        wechatUser1.setCity("Shanghai");
        wechatUser1.setCountry("China");
        wechatUser1.setEducation("Master");
        wechatUser1.setGender(true);
        wechatUser1.setIdentity("310000200001010000");
        wechatUserService.save(wechatUser1);
    }

    @Test
    public void loginSuccess() {
        JSONObject body = new JSONObject();
        body.put("token", "fake_token");
        assertEquals("success", wechatLoginController.login(body));
    }

    @Test
    public void loginInvalidToken() {
        JSONObject body = new JSONObject();
        body.put("token", "invalid_token");
        assertEquals("invalid token", wechatLoginController.login(body));
    }

    @Test
    public void loginNotExistToken() {
        JSONObject body = new JSONObject();
        body.put("token", "not_exist_token");
        assertEquals("not exist", wechatLoginController.login(body));
    }

    @Test
    public void registerSuccess() {
        JSONObject body = new JSONObject();
        body.put("token", "not_exist_token");
        body.put("username", "some_name");
        assertEquals("success", wechatLoginController.register(body));
    }

    @Test
    public void registerExist() {
        JSONObject body = new JSONObject();
        body.put("token", "fake_token");
        body.put("username", "some_name");
        assertEquals("user exist", wechatLoginController.register(body));
    }

    @Test
    public void registerInvalidToken() {
        JSONObject body = new JSONObject();
        body.put("token", "invalid_token");
        body.put("name", "some_name");
        assertEquals("invalid token", wechatLoginController.register(body));
    }
}
