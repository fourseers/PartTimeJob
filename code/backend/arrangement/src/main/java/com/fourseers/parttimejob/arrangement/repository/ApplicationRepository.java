package com.fourseers.parttimejob.arrangement.repository;

import com.fourseers.parttimejob.arrangement.projection.ApplicationProjection;
import com.fourseers.parttimejob.common.entity.Application;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    @Query("select application.applicationId as applicationId, " +
            "application.createTime as createTime, " +
            "application.appliedBeginDate as appliedBeginDate, " +
            "application.appliedEndDate as appliedEndDate, " +
            "application.status as status, " +
            "application.employTime as employTime, " +
            "application.cvId as cvId " +
            "from Application application " +
            "where application.job.jobId = ?1")
    Page<ApplicationProjection> getApplicationsByJobId(Integer jobId, Pageable pageable);

    @Query("from Application app where app.status = true and app.job = ?1")
    List<Application> findApprovedByJob(Job job);

    boolean existsByWechatUserAndJob(WechatUser wechatUser, Job job);
    Application findByWechatUserAndJob(WechatUser wechatUser, Job job);

    @Query("from Application a where a.status = true and a.wechatUser = ?1 " +
            "and not (a.appliedBeginDate > ?3 or a.appliedEndDate < ?2) " +
            "and not (a.job.beginTime > ?5 or a.job.endTime < ?4)")
    List<Application> getAlreadyOccupied(WechatUser wechatUser,
            Date appliedBeginDate, Date appliedEndDate, Time appliedBeginTime, Time appliedEndTime);
}
