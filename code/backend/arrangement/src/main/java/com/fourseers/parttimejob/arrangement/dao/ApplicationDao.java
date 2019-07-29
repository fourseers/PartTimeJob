package com.fourseers.parttimejob.arrangement.dao;

import com.fourseers.parttimejob.common.entity.Application;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.WechatUser;

import java.time.LocalDate;
import java.util.List;

public interface ApplicationDao {

    boolean addOne(Application application);

    List<Application> getAppliedByJob(Job job);

    List<Application> getAppliedByUserAndDate(WechatUser user, LocalDate beginDate, LocalDate endDate);

    boolean haveAlreadyApplied(WechatUser user, Job job);
}
