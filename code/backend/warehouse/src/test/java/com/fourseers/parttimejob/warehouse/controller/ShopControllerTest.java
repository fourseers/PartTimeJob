package com.fourseers.parttimejob.warehouse.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.common.entity.Company;
import com.fourseers.parttimejob.common.entity.Industry;
import com.fourseers.parttimejob.common.entity.MerchantUser;
import com.fourseers.parttimejob.common.entity.Shop;
import com.fourseers.parttimejob.warehouse.dto.ShopDto;
import com.fourseers.parttimejob.warehouse.repository.ShopRepository;
import com.fourseers.parttimejob.warehouse.service.CompanyService;
import com.fourseers.parttimejob.warehouse.service.IndustryService;
import com.fourseers.parttimejob.warehouse.service.MerchantUserService;
import com.fourseers.parttimejob.warehouse.service.ShopService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ShopControllerTest {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private MerchantUserService merchantUserService;

    @Autowired
    private IndustryService industryService;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {

        Industry industry = new Industry();
        industry.setIndustryName("IT");
        industryService.save(industry);

        MerchantUser boss = new MerchantUser();
        boss.setUsername("Tim Cook");
        boss.setPassword("some password");
        merchantUserService.save(boss);

        Company company = new Company();
        company.setCompanyName("Apple");
        companyService.save(company, boss.getUsername());
        boss.setCompany(company);
        merchantUserService.save(boss);

        ShopDto shopDto = new ShopDto();
        shopDto.setShopName("Apple iamp");
        shopDto.setProvince("Shanghai");
        shopDto.setCity("Shanghai");
        shopDto.setAddress("Somewhere in Shanghai");
        shopDto.setLongitude(new Integer(120).floatValue());
        shopDto.setLatitude(new Integer(30).floatValue());
        shopDto.setBrand("Apple");
        shopDto.setIndustry(1);
        shopDto.setIntroduction("Make Apple great again");
        shopService.save(shopDto, boss.getUsername());

        MerchantUser someUserWithoutCompany = new MerchantUser();
        someUserWithoutCompany.setUsername("poor user");
        someUserWithoutCompany.setPassword("some password");
        merchantUserService.save(someUserWithoutCompany);

        MerchantUser anotherBoss = new MerchantUser();
        anotherBoss.setUsername("Luo Yonghao");
        anotherBoss.setPassword("some password");
        merchantUserService.save(anotherBoss);

        Company anotherCompany = new Company();
        anotherCompany.setCompanyName("锤子");
        companyService.save(anotherCompany, anotherBoss.getUsername());
        anotherBoss.setCompany(anotherCompany);
        merchantUserService.save(anotherBoss);

        ShopDto anotherShopDto = new ShopDto();
        anotherShopDto.setShopName("Chuizi somewhere");
        anotherShopDto.setProvince("Sichuan");
        anotherShopDto.setCity("Chendu");
        anotherShopDto.setAddress("Somewhere in Chendu");
        anotherShopDto.setLongitude(new Integer(110).floatValue());
        anotherShopDto.setLatitude(new Integer(30).floatValue());
        anotherShopDto.setBrand("Chuizi");
        anotherShopDto.setIndustry(1);
        anotherShopDto.setIntroduction("Acquire Apple Someday!");
        shopService.save(anotherShopDto, anotherBoss.getUsername());
    }

    @Test
    public void createShopSuccess() throws Exception {

        String username = "Tim Cook";

        JSONObject body = new JSONObject();
        body.fluentPut("shop_name", "Apple iamp 2")
            .fluentPut("province", "Shanghai")
            .fluentPut("city", "Shanghai")
            .fluentPut("address", "Somewhere in Shanghai")
            .fluentPut("longitude", 120)
            .fluentPut("latitude", 30)
            .fluentPut("brand", "Apple")
            .fluentPut("industry", 1)
            .fluentPut("introduction", "Make Apple great again");

        MvcResult result = mockMvc.perform(post("/merchant/shop")
                .header("x-internal-token", username)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));

        Pageable pageable = PageRequest.of(0, 10000);
        List<Shop> shops = shopRepository.findPageByUsername(username, pageable).getContent();

        for (Shop shop : shops) {
            assertNotNull(shop.getCompany());
            assertNotNull(shop.getCompany().getBoss());
            assertNotNull(shop.getCompany().getBoss().getUsername());
            if (shop.getCompany().getBoss().getUsername().equals(username)) {
                return;
            }
        }
        fail();
    }

    @Test
    public void createShopIncorrectParam() throws Exception {

        String username = "Tim Cook";

        JSONObject body = new JSONObject();
        body.fluentPut("shop_name", "Apple iamp 2")
                .fluentPut("province", "Shanghai")
                .fluentPut("city", "Shanghai")
                .fluentPut("address", "Somewhere in Shanghai")
                .fluentPut("longitude", 120)
                .fluentPut("latitude", 30)
                .fluentPut("brand", "Apple")
                .fluentPut("industry", 1);

        MvcResult result = mockMvc.perform(post("/merchant/shop")
                .header("x-internal-token", username)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("incorrect param", response.getString("message"));
    }

    @Test
    public void createShopIncorrectIndustry() throws Exception {

        String username = "Tim Cook";

        JSONObject body = new JSONObject();
        body.fluentPut("shop_name", "Apple iamp 2")
                .fluentPut("province", "Shanghai")
                .fluentPut("city", "Shanghai")
                .fluentPut("address", "Somewhere in Shanghai")
                .fluentPut("longitude", 120)
                .fluentPut("latitude", 30)
                .fluentPut("brand", "Apple")
                .fluentPut("industry", -1);

        MvcResult result = mockMvc.perform(post("/merchant/shop")
                .header("x-internal-token", username)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("incorrect param", response.getString("message"));
    }

    @Test
    public void createShopNameAlreadyExist() throws Exception {

        String username = "Tim Cook";

        JSONObject body = new JSONObject();
        body.fluentPut("shop_name", "Apple iamp")
                .fluentPut("province", "Shanghai")
                .fluentPut("city", "Shanghai")
                .fluentPut("address", "Somewhere in Shanghai")
                .fluentPut("longitude", 120)
                .fluentPut("latitude", 30)
                .fluentPut("brand", "Apple")
                .fluentPut("industry", 1)
                .fluentPut("introduction", "Make Apple great again");

        MvcResult result = mockMvc.perform(post("/merchant/shop")
                .header("x-internal-token", username)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("shop name exists", response.getString("message"));
    }

    @Test
    public void createShopNoCompany() throws Exception {

        String username = "poor user";

        JSONObject body = new JSONObject();
        body.fluentPut("shop_name", "Apple iamp")
                .fluentPut("province", "Shanghai")
                .fluentPut("city", "Shanghai")
                .fluentPut("address", "Somewhere in Shanghai")
                .fluentPut("longitude", 120)
                .fluentPut("latitude", 30)
                .fluentPut("brand", "Apple")
                .fluentPut("industry", 1)
                .fluentPut("introduction", "Make Apple great again");

        MvcResult result = mockMvc.perform(post("/merchant/shop")
                .header("x-internal-token", username)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("user not belong to any company", response.getString("message"));
    }

    @Test
    public void getOneShopSuccess() throws Exception {

        String username = "Tim Cook";

        MvcResult result = mockMvc.perform(get("/merchant/shop")
                .header("x-internal-token", username)
                .param("shop_id", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertNotNull(response.getJSONObject("data"));
        assertEquals("Apple iamp", response.getJSONObject("data").getString("shop_name"));
        assertEquals("success", response.getString("message"));
    }

    @Test
    public void getOneShopNotExist() throws Exception {

        String username = "Tim Cook";

        MvcResult result = mockMvc.perform(get("/merchant/shop")
                .header("x-internal-token", username)
                .param("shop_id", "3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("shop not exist or not belong to", response.getString("message"));
    }

    @Test
    public void getOneShopNotBelongTo() throws Exception {

        String username = "Tim Cook";

        MvcResult result = mockMvc.perform(get("/merchant/shop")
                .header("x-internal-token", username)
                .param("shop_id", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("shop not exist or not belong to", response.getString("message"));
    }

    @Test
    public void getShopsSuccess() throws Exception {

        String username = "Tim Cook";

        MvcResult result = mockMvc.perform(get("/merchant/shops")
                .header("x-internal-token", username)
                .param("page_count", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertNotNull(response.getJSONObject("data"));
        assertEquals(1, response.getJSONObject("data").getJSONArray("content").size());
        assertEquals(1, response.getJSONObject("data").getJSONArray("content").getJSONObject(0).getIntValue("shop_id"));
        assertEquals("success", response.getString("message"));
    }

    @Test
    public void getShopsLastPage() throws Exception {
        String bossname = "Bill Gates";
        MerchantUser boss = new MerchantUser();
        boss.setUsername(bossname);
        boss.setPassword("some password");
        merchantUserService.save(boss);

        Company company = new Company();
        company.setCompanyName("MS");
        companyService.save(company, boss.getUsername());
        boss.setCompany(company);
        merchantUserService.save(boss);

        for (int i = 0; i < 95; i++) {
            ShopDto shopDto = new ShopDto();
            shopDto.setShopName("MS " + i);
            shopDto.setProvince("Shanghai");
            shopDto.setCity("Shanghai");
            shopDto.setAddress("Somewhere in Shanghai");
            shopDto.setLongitude(new Integer(120).floatValue());
            shopDto.setLatitude(new Integer(30).floatValue());
            shopDto.setBrand("MS");
            shopDto.setIndustry(1);
            shopDto.setIntroduction("Make MS great again");
            shopService.save(shopDto, boss.getUsername());
        }

        MvcResult result = mockMvc.perform(get("/merchant/shops")
                .header("x-internal-token", bossname)
                .param("page_count", "9")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertNotNull(response.getJSONObject("data"));
        assertEquals(5, response.getJSONObject("data").getJSONArray("content").size());
        for (int i = 0; i < 5; i++) {
            assertEquals("MS " + Integer.toString(i + 90), response.getJSONObject("data").getJSONArray("content").getJSONObject(i).getString("shop_name"));
        }
        assertEquals("success", response.getString("message"));

    }


    @Test
    public void getShopsNoCompany() throws Exception {

        String username = "poor user";

        MvcResult result = mockMvc.perform(get("/merchant/shops")
                .header("x-internal-token", username)
                .param("page_count", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("no shops", response.getString("message"));
    }

    @Test
    public void getShopsOffsetOverflow() throws Exception {

        String username = "Tim Cook";

        MvcResult result = mockMvc.perform(get("/merchant/shops")
                .header("x-internal-token", username)
                .param("page_count", "666")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("no shops", response.getString("message"));
    }

    @Test
    public void getShopsOffsetNegative() throws Exception {

        String username = "Tim Cook";

        MvcResult result = mockMvc.perform(get("/merchant/shops")
                .header("x-internal-token", username)
                .param("page_count", "-1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("incorrect param", response.getString("message"));
    }

    @Test
    public void updateShopSuccess() throws Exception {
        String username = "Tim Cook";

        JSONObject body = new JSONObject();
        body
                .fluentPut("shop_id", 1)
                .fluentPut("shop_name", "Apple iamp new name")
                .fluentPut("province", "Shanghai")
                .fluentPut("city", "Shanghai")
                .fluentPut("address", "Somewhere in Shanghai")
                .fluentPut("longitude", 120)
                .fluentPut("latitude", 30)
                .fluentPut("brand", "Apple")
                .fluentPut("industry", 1)
                .fluentPut("introduction", "Make Apple great again");

        MvcResult result = mockMvc.perform(put("/merchant/shop")
                .header("x-internal-token", username)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));

        Pageable pageable = PageRequest.of(0, 10000);
        List<Shop> shops = shopRepository.findPageByUsername(username, pageable).getContent();

        for (Shop shop : shops) {
            if (shop.getShopId() == 1) {
                assertEquals("Apple iamp new name", shop.getShopName());
                return;
            }
        }
        fail();
    }

    @Test
    public void updateShopIncorrectParam() throws Exception {
        String username = "Tim Cook";

        JSONObject body = new JSONObject();
        body
                .fluentPut("shop_id", 1)
                .fluentPut("province", "Shanghai")
                .fluentPut("city", "Shanghai")
                .fluentPut("address", "Somewhere in Shanghai")
                .fluentPut("longitude", 120)
                .fluentPut("latitude", 30)
                .fluentPut("brand", "Apple")
                .fluentPut("industry", 1)
                .fluentPut("introduction", "Make Apple great again");

        MvcResult result = mockMvc.perform(put("/merchant/shop")
                .header("x-internal-token", username)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("incorrect param", response.getString("message"));

    }

    @Test
    public void updateShopIncorrectIndustry() throws Exception {
        String username = "Tim Cook";

        JSONObject body = new JSONObject();
        body
                .fluentPut("shop_id", 1)
                .fluentPut("shop_name", "Apple iamp new name")
                .fluentPut("province", "Shanghai")
                .fluentPut("city", "Shanghai")
                .fluentPut("address", "Somewhere in Shanghai")
                .fluentPut("longitude", 120)
                .fluentPut("latitude", 30)
                .fluentPut("brand", "Apple")
                .fluentPut("industry", -1)
                .fluentPut("introduction", "Make Apple great again");

        MvcResult result = mockMvc.perform(put("/merchant/shop")
                .header("x-internal-token", username)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("incorrect param", response.getString("message"));

    }

    @Test
    public void updateShopNameAlreadyExist() throws Exception {
        String username = "Tim Cook";

        JSONObject body = new JSONObject();
        body
                .fluentPut("shop_id", 1)
                .fluentPut("shop_name", "Chuizi somewhere")
                .fluentPut("province", "Shanghai")
                .fluentPut("city", "Shanghai")
                .fluentPut("address", "Somewhere in Shanghai")
                .fluentPut("longitude", 120)
                .fluentPut("latitude", 30)
                .fluentPut("brand", "Apple")
                .fluentPut("industry", 1)
                .fluentPut("introduction", "Make Apple great again");

        MvcResult result = mockMvc.perform(put("/merchant/shop")
                .header("x-internal-token", username)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("shop name exists", response.getString("message"));

    }

    @Test
    public void updateShopNoCompany() throws Exception {
        String username = "poor user";

        JSONObject body = new JSONObject();
        body
                .fluentPut("shop_id", 1)
                .fluentPut("shop_name", "Apple iamp new name")
                .fluentPut("province", "Shanghai")
                .fluentPut("city", "Shanghai")
                .fluentPut("address", "Somewhere in Shanghai")
                .fluentPut("longitude", 120)
                .fluentPut("latitude", 30)
                .fluentPut("brand", "Apple")
                .fluentPut("industry", 1)
                .fluentPut("introduction", "Make Apple great again");

        MvcResult result = mockMvc.perform(put("/merchant/shop")
                .header("x-internal-token", username)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("shop not exist or not belong to", response.getString("message"));

    }

    @Test
    public void updateShopNotBelongTo() throws Exception {
        String username = "Luo Yonghao";

        JSONObject body = new JSONObject();
        body
                .fluentPut("shop_id", 1)
                .fluentPut("shop_name", "Apple iamp new name")
                .fluentPut("province", "Shanghai")
                .fluentPut("city", "Shanghai")
                .fluentPut("address", "Somewhere in Shanghai")
                .fluentPut("longitude", 120)
                .fluentPut("latitude", 30)
                .fluentPut("brand", "Apple")
                .fluentPut("industry", 1)
                .fluentPut("introduction", "Make Apple great again");

        MvcResult result = mockMvc.perform(put("/merchant/shop")
                .header("x-internal-token", username)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("shop not exist or not belong to", response.getString("message"));

    }

    @Test
    public void updateShopNotExist() throws Exception {
        String username = "Luo Yonghao";

        JSONObject body = new JSONObject();
        body
                .fluentPut("shop_id", 666)
                .fluentPut("shop_name", "Apple iamp new name")
                .fluentPut("province", "Shanghai")
                .fluentPut("city", "Shanghai")
                .fluentPut("address", "Somewhere in Shanghai")
                .fluentPut("longitude", 120)
                .fluentPut("latitude", 30)
                .fluentPut("brand", "Apple")
                .fluentPut("industry", 1)
                .fluentPut("introduction", "Make Apple great again");

        MvcResult result = mockMvc.perform(put("/merchant/shop")
                .header("x-internal-token", username)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("shop not exist or not belong to", response.getString("message"));

    }
}
