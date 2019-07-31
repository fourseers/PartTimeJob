package com.fourseers.parttimejob.arrangement.service;

import com.fourseers.parttimejob.arrangement.dto.AppliedTimeDto;
import com.fourseers.parttimejob.arrangement.dto.ApplyDto;
import com.fourseers.parttimejob.arrangement.projection.JobDetailedInfoProjection;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface JobService {

    void save(Job job, int shopId, String username);

    Job findByJobIdAndUsername(int jobId, String username);

    Page<Job> findPageByShopIdAndUsername(int shopId, String username, int pageCount, int pageSize);

    Page<Job> findJobs(WechatUser user, int pageCount);
    Page<Job> findJobsByGeoLocation(WechatUser user, BigDecimal longitude, BigDecimal latitude, int pageCount);
    Page<Job> findPageByUsername(String username, int pageCount, int pageSize);

    boolean apply(WechatUser user, ApplyDto applyDto);
    JobDetailedInfoProjection getJobDetail(int jobId);
    AppliedTimeDto getJobAppliedTime(int jobId);

    void setJobHiringState(Integer jobId, String username, Boolean stop);
}
