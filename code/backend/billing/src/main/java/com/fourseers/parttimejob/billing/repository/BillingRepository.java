package com.fourseers.parttimejob.billing.repository;

import com.fourseers.parttimejob.common.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;

public interface BillingRepository extends JpaRepository<Billing, Integer> {

    @Query("select sum(work.billing.payment) " +
            "from Work work " +
            "where work.workDate >= ?2 " +
            "and work.workDate <= ?3 " +
            "and work.salaryConfirmed = true " +
            "and work.job.shop.company.companyId = ?1")
    Double getBillingAmountByCompanyIdInGivenPeriod(Integer companyId, Date from, Date to);
}