package com.fourseers.parttimejob.billing.repository;

import com.fourseers.parttimejob.billing.projection.UserWorkEntryProjection;
import com.fourseers.parttimejob.billing.projection.WorkBillingProjection;
import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.common.entity.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;

public interface WorkRepository extends JpaRepository<Work, Integer> {

    @Query("select " +
            "work.billing.billId as billId, " +
            "work.job.shop.shopId as shopId, " +
            "work.job.shop.shopName as shopName, " +
            "work.worker.userId as employeeId, " +
            "work.worker.name as employeeName, " +
            "work.workDate as workDate, " +
            "work.job.beginTime as beginTime, " +
            "work.job.endTime as endTime, " +
            "work.billing.payment as payment, " +
            "work.job.jobName as jobName, " +
            "work.salaryConfirmed as paid from Work work " +
            "where work.workDate >= ?2 and work.workDate <= ?3 and work.job.shop.company.companyId = ?1")
    Page<WorkBillingProjection> getBillingsByCompanyIdOrderByBillIdDescInGivenPeriod(Integer companyId, Date fromDate, Date toDate, Pageable pageable);

    Work findByWorkId(Integer workId);

    @Query(value = "select " +
            " w.workId as workId" +
            ",w.workDate as workDate" +
            ",w.salaryConfirmed as salaryConfirmed" +
            ",w.rejected as rejected" +
            ",w.job.jobId as jobId" +
            ",w.job.jobName as jobName" +
            ",b.billId as billId" +
            ",b.payment as payment" +
            ",b.meta as meta" +
            " from Work w left join Billing b" +
            " on w.billing = b where w.worker= ?1")
    Page<UserWorkEntryProjection> getUserWorkEntries(WechatUser user, Pageable pageable);
}

