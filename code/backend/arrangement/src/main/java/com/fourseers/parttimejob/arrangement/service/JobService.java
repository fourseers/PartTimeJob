package com.fourseers.parttimejob.arrangement.service;

import com.fourseers.parttimejob.arrangement.dto.AppliedTimeDto;
import com.fourseers.parttimejob.arrangement.dto.ApplyDto;
import com.fourseers.parttimejob.arrangement.dto.SearchResultDto;
import com.fourseers.parttimejob.arrangement.projection.JobDetailedInfoProjection;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.common.util.GeoUtil;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface JobService {

    void save(List<Job> jobList, int shopId, String username);

    Job findByJobIdAndUsername(int jobId, String username);

    Page<Job> findPageByShopIdAndUsername(int shopId, String username, int pageCount, int pageSize);

    // wechat search
    // based on jpa + jdbc
    Page<Job> queryJobs(GeoUtil.Point location, Double geoRange,
                        Integer daysToCome, Double minSalary, Double maxSalary, String tag, int entryOffset);
    // based on elasticsearch
    SearchResultDto findJobs(WechatUser user, Boolean useTags, int entryOffset) throws IOException;
    SearchResultDto findJobsByGeoLocation(WechatUser user, Boolean useTags, BigDecimal longitude, BigDecimal latitude, int entryOffset) throws IOException;

    Page<Job> findPageByUsername(String username, int pageCount, int pageSize);
    boolean apply(WechatUser user, ApplyDto applyDto);
    List<JobDetailedInfoProjection> getJobDetail(String identifier);
    AppliedTimeDto getJobAppliedTime(int jobId);

    void setJobHiringState(Integer jobId, String username, Boolean stop);
}
