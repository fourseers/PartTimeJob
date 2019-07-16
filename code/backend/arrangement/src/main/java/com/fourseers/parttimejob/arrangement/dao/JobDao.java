package com.fourseers.parttimejob.arrangement.dao;

import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.Shop;
import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobDao {
    void save(Job job);

    Job findByJobId(int jobId);

    List<Job> findByShop(Shop shop);

    Page<Job> findJobs(WechatUser user, int pageCount, int pageSize);

    Page<Job> findJobsByGeoLocation(WechatUser user, float longitude, float latitude, int pageCount, int pageSize);
}
