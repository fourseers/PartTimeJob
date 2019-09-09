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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.UUID;

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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private WorkRepository workRepository;

    private Shop shop;
    private Integer initialScore = 4;

    @Value("${app.wechat_user_prefix}")
    private String WECHAT_USER_PREFIX;

    private String userToken1, userToken2;
    private Job job;
    private WechatUser wechatUser, wechatUser2;

    @Before
    public void before() throws Exception {

        WechatUser wechatUser1 = new WechatUser();
        wechatUser1.setName("SJH");
        wechatUser1.setOpenid("FakeOpenId");
        wechatUser1.setGender(true);
        wechatUser1.setCountry("China");
        wechatUser1.setCity("Shanghai");
        wechatUser1.setEducation(Etc.Education.BELOW_SENIOR);
        wechatUser1.setIdentity("310110123412341234");
        wechatUser1.setPhone("13812345678");
        wechatUser1.setTags(new HashSet<>());
        wechatUserRepository.save(wechatUser1);

        wechatUser = wechatUser1;

        wechatUser1 = new WechatUser();
        wechatUser1.setName("CYJ");
        wechatUser1.setOpenid("FakeOpenId2");
        wechatUser1.setGender(true);
        wechatUser1.setCountry("China");
        wechatUser1.setCity("Shanghai");
        wechatUser1.setEducation(Etc.Education.BELOW_SENIOR);
        wechatUser1.setIdentity("310110123412345678");
        wechatUser1.setPhone("13812345678");
        wechatUser1.setTags(new HashSet<>());
        wechatUserRepository.save(wechatUser1);

        wechatUser2 = wechatUser1;


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
        shop.setLongitude(new BigDecimal(120));
        shop.setLatitude(new BigDecimal(30));
        shop.setBrand("Apple");
        shop.setIndustry(industry);
        shop.setIntroduction("Make Apple great again");
        shopRepository.save(shop);

        job = new Job();
        job.setIdentifier(UUID.randomUUID().toString());
        // we dont care about the rest
        job.setSalary(100.0);
        job.setEndApplyTime(Timestamp.valueOf(LocalDateTime.now()));
        job.setBeginApplyTime(Timestamp.valueOf(LocalDateTime.now()));
        job.setBeginTime(Time.valueOf(LocalTime.now()));
        job.setEndTime(Time.valueOf(LocalTime.now()));
        job.setNeedGender(0);
        job.setNeedAmount(100);
        job.setJobDetail("blablabla");
        job.setJobName("hahaha");
        job.setEducation(Etc.Education.SENIOR_HIGH);
        job.setBeginDate(Date.valueOf(LocalDate.now()));
        job.setEndDate(Date.valueOf(LocalDate.now()));
        job.setShop(shop);
        jobRepository.save(job);

        // insert initial remarks
        Score score = new Score();
        ScoreKey scoreKey = new ScoreKey();
        scoreKey.setWechatUserId(wechatUser1.getUserId());
        scoreKey.setShopId(shop.getShopId());
        score.setScoreId(scoreKey);
        score.setShop(shop);
        score.setWechatUser(wechatUser1); // CYJ userToken1
        score.setScore(initialScore);
        scoreRepository.save(score);
    }

    @After
    public void after() throws Exception {
        scoreRepository.deleteAll();
        workRepository.deleteAll();
        jobRepository.deleteAll();
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
        // user need to actually have worked in the shop to make this work.
        Work work = new Work();
        work.setWorker(wechatUser);
        work.setJob(job);
        workRepository.save(work);

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
        // user need to actually have worked in the shop to make this work.
        Work work = new Work();
        work.setWorker(wechatUser2);
        work.setJob(job);
        workRepository.save(work);

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


    @Test
    public void testAddRemarkNeverWorked() throws Exception {
        JSONObject req = new JSONObject()
                .fluentPut("shop_id", shop.getShopId())
                .fluentPut("score", 5);

        // user 2 add a token
        mockMvc.perform(post("/user/shop/score")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toString())
                .header("x-internal-token", userToken2))
                .andExpect(status().isBadRequest());
    }

}

