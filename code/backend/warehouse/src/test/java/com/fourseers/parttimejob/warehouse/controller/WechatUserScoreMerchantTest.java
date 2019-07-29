package com.fourseers.parttimejob.warehouse.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.common.entity.*;
import com.fourseers.parttimejob.warehouse.repository.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashSet;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class WechatUserScoreMerchantTest {

    @Autowired
    private WechatUserRepository wechatUserRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IndustryRepository industryRepository;

    @Autowired
    private MerchantUserRepository merchantUserRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    private Shop shop;
    private Integer initialScore = 4;

    @Value("${app.wechat_user_prefix}")
    private String WECHAT_USER_PREFIX;

    private String userToken1, userToken2;

    @Before
    public void before() throws Exception {
        WechatUser wechatUser = new WechatUser();
        wechatUser.setName("SJH");
        wechatUser.setOpenid("FakeOpenId");
        wechatUser.setGender(true);
        wechatUser.setCountry("China");
        wechatUser.setCity("Shanghai");
        wechatUser.setEducation(Etc.Education.BELOW_SENIOR);
        wechatUser.setIdentity("310110123412341234");
        wechatUser.setPhone("13812345678");
        wechatUser.setTags(new HashSet<>());
        wechatUserRepository.save(wechatUser);

        wechatUser = new WechatUser();
        wechatUser.setName("CYJ");
        wechatUser.setOpenid("FakeOpenId2");
        wechatUser.setGender(true);
        wechatUser.setCountry("China");
        wechatUser.setCity("Shanghai");
        wechatUser.setEducation(Etc.Education.BELOW_SENIOR);
        wechatUser.setIdentity("310110123412345678");
        wechatUser.setPhone("13812345678");
        wechatUser.setTags(new HashSet<>());
        wechatUserRepository.save(wechatUser);

        userToken2 = WECHAT_USER_PREFIX + "FakeOpenId";
        userToken1 = WECHAT_USER_PREFIX + "FakeOpenId2";

        Industry industry = new Industry();
        industry.setIndustryName("IT");
        industryRepository.save(industry);

        MerchantUser boss = new MerchantUser();
        boss.setUsername("Tim Cook");
        boss.setPassword("some password");
        merchantUserRepository.save(boss);

        Company company = new Company();
        company.setCompanyName("Apple");
        companyRepository.save(company);

        shop = new Shop();
        shop.setShopName("Apple iamp");
        shop.setProvince("310000");
        shop.setCity("310100");
        shop.setAddress("310101");
        shop.setLongitude(new Integer(120).floatValue());
        shop.setLatitude(new Integer(30).floatValue());
        shop.setBrand("Apple");
        shop.setIndustry(industry);
        shop.setIntroduction("Make Apple great again");
        shopRepository.save(shop);

        // insert initial remarks
        Score score = new Score();
        ScoreKey scoreKey = new ScoreKey();
        scoreKey.setWechatUserId(wechatUser.getUserId());
        scoreKey.setShopId(shop.getShopId());
        score.setScoreId(scoreKey);
        score.setShop(shop);
        score.setWechatUser(wechatUser); // CYJ userToken1
        score.setScore(initialScore);
        scoreRepository.save(score);
    }

    @After
    public void after() throws Exception {
        scoreRepository.deleteAll();
        shopRepository.deleteAll();
        companyRepository.deleteAll();
        merchantUserRepository.deleteAll();
        industryRepository.deleteAll();
        wechatUserRepository.deleteAll();
    }

    @Test
    public void testGetShopWithRemarkSuccess() throws Exception {

        MvcResult result = mockMvc.perform(get("/user/shop")
                .param("shop_id",  shop.getShopId().toString())
                .header("x-internal-token", userToken1))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        JSONObject data = response.getJSONObject("data");
        assertNotNull(data);
        assertEquals(data.getInteger("shop_id"), shop.getShopId());
        assertNotNull(data.getString("shop_name"));
        assertEquals(data.getFloat("avg_score"), Float.valueOf(initialScore));
        assertEquals(data.getInteger("user_score"), initialScore);
    }

    @Test
    public void testGetShopWithoutRemarkSuccess() throws Exception {

        MvcResult result = mockMvc.perform(get("/user/shop")
                .param("shop_id",  shop.getShopId().toString())
                .header("x-internal-token", userToken2))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        JSONObject data = response.getJSONObject("data");
        assertNotNull(data);
        assertEquals(data.getInteger("shop_id"), shop.getShopId());
        assertNotNull(data.getString("shop_name"));
        assertEquals(data.getFloat("avg_score"), Float.valueOf(initialScore));
        assertNull(data.getInteger("user_score"));
    }

    @Test
    public void testGetShopInvalidShopId() throws Exception {
        MvcResult result = mockMvc.perform(get("/user/shop")
                .param("shop_id",  "1000")
                .header("x-internal-token", userToken1))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testAddRemark() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("shop_id", shop.getShopId())
                .fluentPut("score", 5);

        // user 2 add a token
        mockMvc.perform(post("/user/shop/score")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toString())
                .header("x-internal-token", userToken2))
                .andExpect(status().isOk());

        MvcResult result = mockMvc.perform(get("/user/shop")
                .param("shop_id",  shop.getShopId().toString())
                .header("x-internal-token", userToken1))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        JSONObject data = response.getJSONObject("data");
        assertNotNull(data);
        // should be 4.5
        assertEquals(data.getFloat("avg_score"), Float.valueOf(4.5f));
    }

    @Test
    public void testModifyRemark() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("shop_id", shop.getShopId())
                .fluentPut("score", 5);

        // user 1 modify his/her score
        mockMvc.perform(post("/user/shop/score")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toString())
                .header("x-internal-token", userToken1))
                .andExpect(status().isOk());

        MvcResult result = mockMvc.perform(get("/user/shop")
                .param("shop_id",  shop.getShopId().toString())
                .header("x-internal-token", userToken1))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        JSONObject data = response.getJSONObject("data");
        assertNotNull(data);
        // should be 5 as the original one is modified
        assertEquals(data.getFloat("avg_score"), Float.valueOf(5));
    }

    @Test
    public void testModifyRemarkInvalidShopId() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("shop_id", -1)
                .fluentPut("score", 5);

        // user 1 modify his/her score
        mockMvc.perform(post("/user/shop/score")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toString())
                .header("x-internal-token", userToken1))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testRemarkInvalidScore() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("shop_id", shop.getShopId())
                .fluentPut("score", 10); // should be [0,5]

        // user 1 modify his/her score
        mockMvc.perform(post("/user/shop/score")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toString())
                .header("x-internal-token", userToken1))
                .andExpect(status().is4xxClientError());
    }
}

