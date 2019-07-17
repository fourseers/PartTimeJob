package com.fourseers.parttimejob.arrangement.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.arrangement.service.JobService;
import com.fourseers.parttimejob.common.entity.Job;
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

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class JobControllerTest {

    @Autowired
    private JobService jobService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void addJobSuccess() throws Exception {

        String managerName = "葛越";

        JSONObject body = new JSONObject();
        JSONArray tagList = new JSONArray();
        tagList.fluentAdd(1)
               .fluentAdd(2);
        body.fluentPut("shop_id", 1)
            .fluentPut("job_name", "seller")
            .fluentPut("begin_date", "Tue, 16 Jul 2019 16:00:00 GMT")
            .fluentPut("end_date", "Wed, 17 Jul 2019 16:00:00 GMT")
            .fluentPut("job_detail", "sell Apple products")
            .fluentPut("need_gender", 2)
            .fluentPut("need_amount", 10)
            .fluentPut("begin_apply_date", "Sun, 14 Jul 2019 16:00:00 GMT")
            .fluentPut("end_apply_date", "Mon, 15 Jul 2019 16:00:00 GMT")
            .fluentPut("education", "大学本科以上")
            .fluentPut("tag_list", tagList)
            .fluentPut("salary", 100);

        MvcResult result = mockMvc.perform(post("/merchant/job")
                .header("x-internal-token", managerName)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));

        Job job = jobService.findByJobIdAndUsername(2, managerName);
        assertNotNull(job);
        assertEquals(new Integer(2), job.getJobId());
        assertEquals(new Integer(1), job.getShop().getShopId());
        assertEquals(2, job.getTagList().size());
    }

    @Test
    public void addJobIncorrectTag() throws Exception {

        String managerName = "葛越";

        JSONObject body = new JSONObject();
        JSONArray tagList = new JSONArray();
        tagList.fluentAdd(1)
               .fluentAdd(2)
               .fluentAdd(3);
        body.fluentPut("shop_id", 1)
                .fluentPut("job_name", "seller")
                .fluentPut("begin_date", "Tue, 16 Jul 2019 16:00:00 GMT")
                .fluentPut("end_date", "Wed, 17 Jul 2019 16:00:00 GMT")
                .fluentPut("job_detail", "sell Apple products")
                .fluentPut("need_gender", 2)
                .fluentPut("need_amount", 10)
                .fluentPut("begin_apply_date", "Sun, 14 Jul 2019 16:00:00 GMT")
                .fluentPut("end_apply_date", "Mon, 15 Jul 2019 16:00:00 GMT")
                .fluentPut("education", "大学本科以上")
                .fluentPut("tag_list", tagList)
                .fluentPut("salary", 100);

        MvcResult result = mockMvc.perform(post("/merchant/job")
                .header("x-internal-token", managerName)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("incorrect tag", response.getString("message"));
    }

    @Test
    public void addJobShopNotBelongTo() throws Exception {

        String managerName = "葛越";

        JSONObject body = new JSONObject();
        JSONArray tagList = new JSONArray();
        tagList.fluentAdd(1)
               .fluentAdd(2);
        body.fluentPut("shop_id", 3)
            .fluentPut("job_name", "seller")
                .fluentPut("begin_date", "Tue, 16 Jul 2019 16:00:00 GMT")
                .fluentPut("end_date", "Wed, 17 Jul 2019 16:00:00 GMT")
                .fluentPut("job_detail", "sell Apple products")
                .fluentPut("need_gender", 2)
                .fluentPut("need_amount", 10)
                .fluentPut("begin_apply_date", "Sun, 14 Jul 2019 16:00:00 GMT")
                .fluentPut("end_apply_date", "Mon, 15 Jul 2019 16:00:00 GMT")
            .fluentPut("education", "大学本科以上")
            .fluentPut("tag_list", tagList)
            .fluentPut("salary", 100);

        MvcResult result = mockMvc.perform(post("/merchant/job")
                .header("x-internal-token", managerName)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("shop not exist or not belong to", response.getString("message"));
    }

    @Test
    public void addJobShopNotExist() throws Exception {

        String managerName = "葛越";

        JSONObject body = new JSONObject();
        JSONArray tagList = new JSONArray();
        tagList.fluentAdd(1)
                .fluentAdd(2);
        body.fluentPut("shop_id", 3)
                .fluentPut("job_name", "seller")
                .fluentPut("begin_date", "Tue, 16 Jul 2019 16:00:00 GMT")
                .fluentPut("end_date", "Wed, 17 Jul 2019 16:00:00 GMT")
                .fluentPut("job_detail", "sell Apple products")
                .fluentPut("need_gender", 2)
                .fluentPut("need_amount", 10)
                .fluentPut("begin_apply_date", "Sun, 14 Jul 2019 16:00:00 GMT")
                .fluentPut("end_apply_date", "Mon, 15 Jul 2019 16:00:00 GMT")
                .fluentPut("education", "大学本科以上")
                .fluentPut("tag_list", tagList)
                .fluentPut("salary", 100);

        MvcResult result = mockMvc.perform(post("/merchant/job")
                .header("x-internal-token", managerName)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("shop not exist or not belong to", response.getString("message"));
    }

    @Test
    public void addJobBeginAfterEnd() throws Exception {

        String managerName = "葛越";

        JSONObject body = new JSONObject();
        JSONArray tagList = new JSONArray();
        tagList.fluentAdd(1)
                .fluentAdd(2);
        body.fluentPut("shop_id", 2)
                .fluentPut("job_name", "seller")
                .fluentPut("begin_date", "Thu, 18 Jul 2019 16:00:00 GMT")
                .fluentPut("end_date", "Wed, 17 Jul 2019 16:00:00 GMT")
                .fluentPut("job_detail", "sell Apple products")
                .fluentPut("need_gender", 2)
                .fluentPut("need_amount", 10)
                .fluentPut("begin_apply_date", "Sun, 14 Jul 2019 16:00:00 GMT")
                .fluentPut("end_apply_date", "Mon, 15 Jul 2019 16:00:00 GMT")
                .fluentPut("education", "大学本科以上")
                .fluentPut("tag_list", tagList)
                .fluentPut("salary", 100);

        MvcResult result = mockMvc.perform(post("/merchant/job")
                .header("x-internal-token", managerName)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("time incorrect", response.getString("message"));
    }

    @Test
    public void addJobApplyTimeAfterBegin() throws Exception {

        String managerName = "葛越";

        JSONObject body = new JSONObject();
        JSONArray tagList = new JSONArray();
        tagList.fluentAdd(1)
                .fluentAdd(2);
        body.fluentPut("shop_id", 2)
                .fluentPut("job_name", "seller")
                .fluentPut("begin_date", "Tue, 16 Jul 2019 16:00:00 GMT")
                .fluentPut("end_date", "Wed, 17 Jul 2019 16:00:00 GMT")
                .fluentPut("job_detail", "sell Apple products")
                .fluentPut("need_gender", 2)
                .fluentPut("need_amount", 10)
                .fluentPut("begin_apply_date", "Sun, 14 Jul 2019 16:00:00 GMT")
                .fluentPut("end_apply_date", "Tue, 16 Jul 2019 16:00:01 GMT")
                .fluentPut("education", "大学本科以上")
                .fluentPut("tag_list", tagList)
                .fluentPut("salary", 100);

        MvcResult result = mockMvc.perform(post("/merchant/job")
                .header("x-internal-token", managerName)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("time incorrect", response.getString("message"));
    }

    @Test
    public void addJobAcquireBeginAfterEnd() throws Exception {

        String managerName = "葛越";

        JSONObject body = new JSONObject();
        JSONArray tagList = new JSONArray();
        tagList.fluentAdd(1)
                .fluentAdd(2);
        body.fluentPut("shop_id", 2)
                .fluentPut("job_name", "seller")
                .fluentPut("begin_date", "Tue, 16 Jul 2019 16:00:00 GMT")
                .fluentPut("end_date", "Wed, 17 Jul 2019 16:00:00 GMT")
                .fluentPut("job_detail", "sell Apple products")
                .fluentPut("need_gender", 2)
                .fluentPut("need_amount", 10)
                .fluentPut("begin_apply_date", "Sun, 14 Jul 2019 16:00:00 GMT")
                .fluentPut("end_apply_date", "Sat, 13 Jul 2019 16:00:00 GMT")
                .fluentPut("education", "大学本科以上")
                .fluentPut("tag_list", tagList)
                .fluentPut("salary", 100);

        MvcResult result = mockMvc.perform(post("/merchant/job")
                .header("x-internal-token", managerName)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("time incorrect", response.getString("message"));
    }

    @Test
    public void addJobSalaryNegative() throws Exception {

        String managerName = "葛越";

        JSONObject body = new JSONObject();
        JSONArray tagList = new JSONArray();
        tagList.fluentAdd(1)
                .fluentAdd(2);
        body.fluentPut("shop_id", 1)
                .fluentPut("job_name", "seller")
                .fluentPut("begin_date", "Wed Jul 17 2019 00:00:00 GMT+0800")
                .fluentPut("end_date", "Thu Jul 18 2019 00:00:00 GMT+0800")
                .fluentPut("job_detail", "sell Apple products")
                .fluentPut("need_gender", 2)
                .fluentPut("need_amount", 10)
                .fluentPut("begin_apply_date", "Mon Jul 15 2019 00:00:00 GMT+0800")
                .fluentPut("end_apply_date", "Tue Jul 16 2019 00:00:00 GMT+0800")
                .fluentPut("education", "大学本科以上")
                .fluentPut("tag_list", tagList)
                .fluentPut("salary", -1);

        MvcResult result = mockMvc.perform(post("/merchant/job")
                .header("x-internal-token", managerName)
                .content(body.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("incorrect param", response.getString("message"));
    }

    @Test
    public void getOnePageJobsSuccess() throws Exception {
        String managerName = "葛越";

        MvcResult result = mockMvc.perform(get("/merchant/jobs")
                .header("x-internal-token", managerName)
                .param("page_count", "0"))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));
        assertNotNull(response.getJSONObject("data"));
        assertNotNull(response.getJSONObject("data").getJSONArray("content"));
        assertEquals(1, response.getJSONObject("data").getJSONArray("content").size());
    }

    @Test
    public void getOnePageJobsNoExist() throws Exception {
        String managerName = "罗永浩";

        MvcResult result = mockMvc.perform(get("/merchant/jobs")
                .header("x-internal-token", managerName)
                .param("page_count", "0"))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("job not exist", response.getString("message"));
    }

    @Test
    public void getOnePageJobsNoCompany() throws Exception {
        String managerName = "poor user";

        MvcResult result = mockMvc.perform(get("/merchant/jobs")
                .header("x-internal-token", managerName)
                .param("page_count", "0"))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("user does not belong to a company", response.getString("message"));
    }

    @Test
    public void getOnePageJobsInOneShopSuccess() throws Exception {
        String managerName = "葛越";

        MvcResult result = mockMvc.perform(get("/merchant/jobs")
                .header("x-internal-token", managerName)
                .param("shop_id", "2")
                .param("page_count", "0"))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));
        assertNotNull(response.getJSONObject("data"));
        assertNotNull(response.getJSONObject("data").getJSONArray("content"));
        assertEquals(1, response.getJSONObject("data").getJSONArray("content").size());
        assertEquals("seller", response.getJSONObject("data").getJSONArray("content").getJSONObject(0).getString("job_name"));
    }

    @Test
    public void getOnePageJobsInOneShopNoExist() throws Exception {
        String managerName = "葛越";

        MvcResult result = mockMvc.perform(get("/merchant/jobs")
                .header("x-internal-token", managerName)
                .param("page_count", "0")
                .param("shop_id", "4"))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("job not exist", response.getString("message"));
    }

    @Test
    public void getOnePageJobsInOneShopNoCompany() throws Exception {
        String managerName = "poor user";

        MvcResult result = mockMvc.perform(get("/merchant/jobs")
                .header("x-internal-token", managerName)
                .param("shop_id", "1")
                .param("page_count", "0"))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("user does not belong to a company", response.getString("message"));
    }

    @Test
    public void getOnePageJobsInOneShopNotBelongTo() throws Exception {
        String managerName = "罗永浩";

        MvcResult result = mockMvc.perform(get("/merchant/jobs")
                .header("x-internal-token", managerName)
                .param("shop_id", "1")
                .param("page_count", "0"))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("shop not exist or not belong to", response.getString("message"));
    }

    @Test
    public void getOnePageJobsInOneShopNotExist() throws Exception {
        String managerName = "葛越";

        MvcResult result = mockMvc.perform(get("/merchant/jobs")
                .header("x-internal-token", managerName)
                .param("shop_id", "666")
                .param("page_count", "0"))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("shop not exist or not belong to", response.getString("message"));
    }

    @Test
    public void getOneJobSuccess() throws Exception {
        String managerName = "葛越";

        MvcResult result = mockMvc.perform(get("/merchant/job")
                .header("x-internal-token", managerName)
                .param("job_id", "1"))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("success", response.getString("message"));
        assertNotNull(response.getJSONObject("data"));
        assertNotNull(response.getJSONObject("data"));
        assertNotNull(response.getJSONObject("data").getString("job_name"));
        assertEquals("seller", response.getJSONObject("data").getString("job_name"));
    }

    @Test
    public void getOneJobNoCompany() throws Exception {
        String managerName = "poor user";

        MvcResult result = mockMvc.perform(get("/merchant/job")
                .header("x-internal-token", managerName)
                .param("job_id", "1"))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("user does not belong to a company", response.getString("message"));
    }

    @Test
    public void getOneJobNotBelongTo() throws Exception {
        String managerName = "罗永浩";

        MvcResult result = mockMvc.perform(get("/merchant/job")
                .header("x-internal-token", managerName)
                .param("job_id", "1"))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("job not exist or not belong to", response.getString("message"));
    }

    @Test
    public void getOneJobNotExist() throws Exception {
        String managerName = "葛越";

        MvcResult result = mockMvc.perform(get("/merchant/job")
                .header("x-internal-token", managerName)
                .param("job_id", "4"))
                .andExpect(status().is(400))
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertEquals("job not exist or not belong to", response.getString("message"));
    }

    @Test
    public void getOnePageJobsWithManyPages() throws Exception {
        String bossname = "Bill Gates";
        for (int j = 0; j < 49; j++) {
            for (int i = 5; i < 7; i++) {
                Job job = new Job();
                job.setJobName("seller");
                job.setBeginDate(new Timestamp(1563235200000L));
                job.setEndDate(new Timestamp(1563235300000L));
                job.setNeedGender(2);
                job.setNeedAmount(10);
                job.setBeginApplyDate(new Timestamp(1563235000000L));
                job.setEndApplyDate(new Timestamp(1563235100000L));
                job.setEducation("大学本科以上");
                job.setTagList(null);
                job.setSalary(100d);
                job.setJobDetail("sell");
                jobService.save(job, i, bossname);
            }
        }

        MvcResult result = mockMvc.perform(get("/merchant/jobs")
                .header("x-internal-token", bossname)
                .param("page_count", "9")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertNotNull(response.getJSONObject("data"));
        assertEquals(8, response.getJSONObject("data").getJSONArray("content").size());
        for (int i = 0; i < 8; i++) {
            assertEquals(new Integer(9 - i), response.getJSONObject("data").getJSONArray("content").getJSONObject(i).getInteger("job_id"));
        }
        assertEquals("success", response.getString("message"));

    }

    @Test
    public void getOnePageJobsInOneShopWithManyPages() throws Exception {
        String bossname = "Bill Gates";
        for (int j = 0; j < 49; j++) {
            for (int i = 5; i < 7; i++) {
                Job job = new Job();
                job.setJobName("seller");
                job.setBeginDate(new Timestamp(1563235200000L));
                job.setEndDate(new Timestamp(1563235300000L));
                job.setNeedGender(2);
                job.setNeedAmount(10);
                job.setBeginApplyDate(new Timestamp(1563235000000L));
                job.setEndApplyDate(new Timestamp(1563235100000L));
                job.setEducation("大学本科以上");
                job.setTagList(null);
                job.setSalary(100d);
                job.setJobDetail("sell");
                jobService.save(job, i, bossname);
            }
        }

        MvcResult result = mockMvc.perform(get("/merchant/jobs")
                .header("x-internal-token", bossname)
                .param("page_count", "4")
                .param("shop_id", "5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject response = JSON.parseObject(result.getResponse().getContentAsString());
        assertNotNull(response.getJSONObject("data"));
        assertEquals(9, response.getJSONObject("data").getJSONArray("content").size());
        for (int i = 0; i < 8; i++) {
            assertEquals(new Integer(18 - i * 2), response.getJSONObject("data").getJSONArray("content").getJSONObject(i).getInteger("job_id"));
        }
        assertEquals("success", response.getString("message"));

    }

}
