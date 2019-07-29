package com.fourseers.parttimejob.arrangement.repository;

import com.fourseers.parttimejob.common.entity.Application;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    @Query("from Application app where app.status = true and app.job = ?1")
    List<Application> findApprovedByJob(Job job);

    @Query("from Application app where app.wechatUser = ?1 and app.status = true " +
            "and not (app.appliedEndDate < ?2 and app.appliedBeginDate > ?3)")
    List<Application> findApprovedByUserAndDate(WechatUser user, Date beginDate, Date endDate);

    boolean existsByWechatUserAndJob(WechatUser wechatUser, Job job);
    Application findByWechatUserAndJob(WechatUser wechatUser, Job job);
}
