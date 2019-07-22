package com.fourseers.parttimejob.billing.repository;

import com.fourseers.parttimejob.billing.projection.BillingProjection;
import com.fourseers.parttimejob.common.entity.Billing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BillingRepository extends JpaRepository<Billing, Integer> {

    @Query("select " +
            "billing.billId as billId, " +
            "billing.shop.shopId as shopId, " +
            "billing.shop.shopName as shopName, " +
            "billing.employee.userId as employeeId, " +
            "billing.employee.name as employeeName, " +
            "billing.job.beginDate as beginTime, " +
            "billing.job.endDate as endTime, " +
            "billing.job.salary as payment, " +
            "billing.job.jobName as jobName, " +
            "billing.paid as paid from Billing billing " +
            "where billing.shop.company.companyId = ?1")
    Page<BillingProjection> getBillingsByCompanyIdOrderByBillIdDesc(Integer companyId, Pageable pageable);

    Billing findByBillId(Integer billId);
}
