package com.fourseers.parttimejob.arrangement.repository;

import com.fourseers.parttimejob.arrangement.projection.WorkNotifyProjection;
import com.fourseers.parttimejob.arrangement.projection.WorkProjection;
import com.fourseers.parttimejob.arrangement.projection.WorkStatusProjection;
import com.fourseers.parttimejob.common.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Integer> {

    Work getByJobAndWorker(Job job, WechatUser wechatUser);

    @Query("select " +
            "work.workId as workId, " +
            "work.worker.name as employeeName, " +
            "work.job.shop.shopName as shopName, " +
            "work.workDate as workDate, " +
            "work.job.beginTime as beginTime, " +
            "work.job.endTime as endTime, " +
            "work.checkin as checkin, " +
            "work.checkout as checkout, " +
            "work.job.jobName as jobName, " +
            "work.score as score, " +
            "work.job.salary as payment, " +
            "work.salaryConfirmed as paid from Work work " +
            "where work.job.shop = ?1")
    Page<WorkProjection> findPageByShopOrderByWorkIdDesc(Shop shop, Pageable pageable);

    @Query("select " +
            "work.workId as workId, " +
            "work.worker.name as employeeName, " +
            "work.job.shop.shopName as shopName, " +
            "work.workDate as workDate, " +
            "work.job.beginTime as beginTime, " +
            "work.job.endTime as endTime, " +
            "work.checkin as checkin, " +
            "work.checkout as checkout, " +
            "work.job.jobName as jobName, " +
            "work.score as score, " +
            "work.job.salary as payment, " +
            "work.salaryConfirmed as paid," +
            "work.rejected as rejected from Work work " +
            "where work.job.shop.company = ?1")
    Page<WorkProjection> findPageByCompanyOrderByWorkIdDesc(Company company, Pageable pageable);

    Work findByWorkId(int workId);

    @Query("from Work w where w.workDate = current_date and w.worker = ?1 and w.job = ?2")
    Work findTodayByUserAndJob(WechatUser user, Job job);

    List<Work> findAllByWorker(WechatUser wechatUser);

    @Query("select " +
            "sum(case when work.checkin > work.expectedCheckin then 1 else 0 end) as lateRate, " +
            "sum(case when work.checkout > work.expectedCheckout then 1 else 0 end) as leaveEarlyRate, " +
            "1 as attendRate " +
            "from Work work " +
            "where work.job.shop.shopId = ?1 and work.workDate >= ?2 and work.workDate <= ?3")
    WorkStatusProjection getWorkStatus(Integer shopId, Date from, Date to);

    @Query("select " +
            "work.worker.name as username," +
            "work.worker.phone as phone, " +
            "work.job.jobName as jobname, " +
            "work.expectedCheckin as time " +
            "from Work work " +
            "where work.checkin = null and work.workDate = current_date and abs(work.expectedCheckin - current_time) < 30")
    List<WorkNotifyProjection> getNotCheckedIn();

    @Query("select " +
            "work.worker.name as username," +
            "work.worker.phone as phone, " +
            "work.job.jobName as jobname, " +
            "work.expectedCheckout as time " +
            "from Work work " +
            "where work.checkout = null and work.workDate = current_date and abs(work.expectedCheckout - current_time) < 30")
    List<WorkNotifyProjection> getNotCheckedOut();
}
