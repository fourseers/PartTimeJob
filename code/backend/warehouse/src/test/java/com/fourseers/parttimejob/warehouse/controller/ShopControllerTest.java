package com.fourseers.parttimejob.warehouse.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.warehouse.dto.ShopDto;
import com.fourseers.parttimejob.warehouse.entity.Company;
import com.fourseers.parttimejob.warehouse.entity.MerchantUser;
import com.fourseers.parttimejob.warehouse.entity.Shop;
import com.fourseers.parttimejob.warehouse.repository.ShopRepository;
import com.fourseers.parttimejob.warehouse.service.CompanyService;
import com.fourseers.parttimejob.warehouse.service.MerchantUserService;
import com.fourseers.parttimejob.warehouse.service.ShopService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private ShopRepository shopRepository;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
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
        shopDto.setIndustry("IT");
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
        anotherShopDto.setIndustry("IT");
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
            .fluentPut("industry", "IT")
            .fluentPut("introduction", "Make Apple great again");

        MvcResult result = mockMvc.perform(post("/merchant/shop/")
                .header("x-internal-token", username)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));

        List<Shop> shops = shopRepository.findAllByUsername(username);

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
                .fluentPut("industry", "IT");

        MvcResult result = mockMvc.perform(post("/merchant/shop/")
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
                .fluentPut("industry", "IT")
                .fluentPut("introduction", "Make Apple great again");

        MvcResult result = mockMvc.perform(post("/merchant/shop/")
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
                .fluentPut("industry", "IT")
                .fluentPut("introduction", "Make Apple great again");

        MvcResult result = mockMvc.perform(post("/merchant/shop/")
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

        MvcResult result = mockMvc.perform(get("/merchant/shop/")
                .header("x-internal-token", username)
                .param("shop_id", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertNotNull(response.getJSONObject("data"));
        assertNotNull(response.getJSONObject("data").getJSONObject("shop"));
        assertEquals("Apple iamp", response.getJSONObject("data").getJSONObject("shop").getString("shop_name"));
        assertEquals("success", response.getString("message"));
    }

    @Test
    public void getOneShopNotExist() throws Exception {

        String username = "Tim Cook";

        MvcResult result = mockMvc.perform(get("/merchant/shop/")
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

        MvcResult result = mockMvc.perform(get("/merchant/shop/")
                .header("x-internal-token", username)
                .param("shop_id", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("shop not exist or not belong to", response.getString("message"));
    }

    @Test
    public void getAllShopsSuccess() throws Exception {

        String username = "Tim Cook";

        MvcResult result = mockMvc.perform(get("/merchant/shop/")
                .header("x-internal-token", username)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertNotNull(response.getJSONObject("data"));
        assertNotNull(response.getJSONObject("data").getJSONArray("shops"));
        assertEquals(1, response.getJSONObject("data").getJSONArray("shops").size());
        assertEquals(1, response.getJSONObject("data").getJSONArray("shops").getJSONObject(0).getIntValue("shop_id"));
        assertEquals("success", response.getString("message"));
    }

    @Test
    public void getAllShopsNoCompany() throws Exception {

        String username = "poor user";

        MvcResult result = mockMvc.perform(get("/merchant/shop/")
                .header("x-internal-token", username)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("no shops", response.getString("message"));
    }

}
