package com.fourseers.parttimejob.arrangement.dao;

import com.fourseers.parttimejob.arrangement.projection.JobDetailedInfoProjection;
import com.fourseers.parttimejob.common.entity.Company;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.Shop;
import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface JobDao {
    void save(Job job);

    Job findByJobId(int jobId);

    Page<Job> findJobs(WechatUser user, int pageCount, int pageSize);

    Page<Job> findJobsByGeoLocation(WechatUser user, BigDecimal longitude, BigDecimal latitude, int pageCount, int pageSize);
    Page<Job> findPageByShop(Shop shop, int pageCount, int pageSize);

    Page<Job> findPageByCompany(Company company, int pageCount, int pageSize);

    JobDetailedInfoProjection getJobDetail(int jobId);
}
