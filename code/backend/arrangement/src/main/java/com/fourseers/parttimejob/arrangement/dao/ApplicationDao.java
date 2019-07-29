package com.fourseers.parttimejob.arrangement.dao;

import com.fourseers.parttimejob.arrangement.projection.ApplicationProjection;
import com.fourseers.parttimejob.common.entity.Application;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.data.domain.Page;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface ApplicationDao {

    boolean addOne(Application application);

    Page<ApplicationProjection> getApplicationsByJobId(Integer jobId, int pageCount, int pageSize);

    List<Application> getAppliedByJob(Job job);

    boolean haveAlreadyApplied(WechatUser user, Job job);

    Application findByApplicationId(Integer applicationId);

    void update(Application application);

    List<Application> findWithin(WechatUser wechatUser, Date beginDate, Date endDate, Time beginTime, Time endTime);
}
