package com.fourseers.parttimejob.arrangement.dao;

import com.fourseers.parttimejob.arrangement.projection.ApplicationProjection;
import com.fourseers.parttimejob.common.entity.Application;
import org.springframework.data.domain.Page;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.WechatUser;

import java.time.LocalDate;
import java.util.List;

public interface ApplicationDao {

    boolean addOne(Application application);

    Page<ApplicationProjection> getApplicationsByJobId(Integer jobId, int pageCount, int pageSize);

    List<Application> getAppliedByJob(Job job);

    List<Application> getAppliedByUserAndDate(WechatUser user, LocalDate beginDate, LocalDate endDate);

    boolean haveAlreadyApplied(WechatUser user, Job job);

    Application findByApplicationId(Integer applicationId);

    void update(Application application);
}
