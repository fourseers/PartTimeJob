package com.fourseers.parttimejob.arrangement.service;

import com.fourseers.parttimejob.arrangement.dto.AppliedTimeDto;
import com.fourseers.parttimejob.arrangement.dto.ApplyDto;
import com.fourseers.parttimejob.arrangement.dto.SearchResultDto;
import com.fourseers.parttimejob.arrangement.projection.JobDetailedInfoProjection;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface JobService {

    void save(List<Job> jobList, int shopId, String username);

    Job findByJobIdAndUsername(int jobId, String username);

    Page<Job> findPageByShopIdAndUsername(int shopId, String username, int pageCount, int pageSize);

    SearchResultDto findJobs(WechatUser user, Boolean useTags, int entryOffset) throws IOException;
    SearchResultDto findJobsByGeoLocation(WechatUser user, Boolean useTags, BigDecimal longitude, BigDecimal latitude, int pageCount) throws IOException;
    Page<Job> findPageByUsername(String username, int pageCount, int pageSize);

    boolean apply(WechatUser user, ApplyDto applyDto);
    JobDetailedInfoProjection getJobDetail(int jobId);
    AppliedTimeDto getJobAppliedTime(int jobId);

    void setJobHiringState(Integer jobId, String username, Boolean stop);
}
