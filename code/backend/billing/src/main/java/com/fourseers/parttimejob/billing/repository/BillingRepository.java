package com.fourseers.parttimejob.billing.repository;

import com.fourseers.parttimejob.billing.projection.BillingStatusProjection;
import com.fourseers.parttimejob.common.entity.Billing;
import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface BillingRepository extends JpaRepository<Billing, Integer> {

    @Query("select sum(work.billing.payment) " +
            "from Work work " +
            "where work.workDate >= ?2 " +
            "and work.workDate <= ?3 " +
            "and work.salaryConfirmed = true " +
            "and work.job.shop.company.companyId = ?1")
    Double getBillingAmountByCompanyIdInGivenPeriod(Integer companyId, Date from, Date to);

    @Query("select work.job.shop.shopId as shopId, " +
            "work.job.shop.shopName as shopName, " +
            "sum(work.billing.payment) as payment " +
            "from Work work " +
            "where work.workDate >= ?2 " +
            "and work.workDate <= ?3 " +
            "and work.salaryConfirmed = true " +
            "and work.job.shop.company.companyId = ?1 " +
            "group by work.job.shop.shopId " +
            "order by work.job.shop.shopId")
    List<BillingStatusProjection> getBillingStatusByCompanyIdInGivenPeriod(Integer companyId, Date from, Date to);

    @Query("select sum(b.payment) from Billing b" +
            " where b.work.worker = ?1 and (b.meta is null or length(trim(b.meta)) = 0)")
    Double getUserBalance(WechatUser user);

    @Query(value = "from Billing b where (b.meta is null or length(trim(b.meta)) = 0)" +
            " and b.work.worker = ?1")
    List<Billing> getBillingsToDraw(WechatUser user);

    @Query(value = "from Billing b where b.meta is not null order by b.updateTime DESC",
        countQuery = "select COUNT(b) from Billing b where b.meta is not null order by b.updateTime DESC")
    Page<Billing> getBillingsDrawn(WechatUser user, Pageable pageRequest);
}
