package com.fourseers.parttimejob.arrangement.service;

import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.data.domain.Page;

import java.util.List;
import org.springframework.data.domain.Page;

public interface JobService {

    void save(Job job, int shopId, String username);

    Job findByJobIdAndUsername(int jobId, String username);

    Page<Job> findPageByShopIdAndUsername(int shopId, String username, int pageCount, int pageSize);

    Page<Job> findJobs(WechatUser user, int pageCount);
    Page<Job> findJobsByGeoLocation(WechatUser user, float longitude, float latitude, int pageCount);
    Page<Job> findPageByUsername(String username, int pageCount, int pageSize);

    boolean apply(WechatUser user, int jobId, String cvId);
}
