package com.fourseers.parttimejob.arrangement.service;

import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.data.domain.Page;

import java.util.List;

public interface JobService {

    void save(Job job, int shopId, String username);

    Job findByJobIdAndUsername(int jobId, String username);

    List<Job> findByShopIdAndUsername(int shopId, String username);

    List<Job> findByUsername(String username);

    Page<Job> findJobs(WechatUser user, int pageCount);

    Page<Job> findJobsByGeoLocation(WechatUser user, float longitude, float latitude, int pageCount);
}
