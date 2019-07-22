package com.fourseers.parttimejob.arrangement.repository;

import com.fourseers.parttimejob.arrangement.projection.WorkProjection;
import com.fourseers.parttimejob.common.entity.Company;
import com.fourseers.parttimejob.common.entity.Shop;
import com.fourseers.parttimejob.common.entity.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WorkRepository extends JpaRepository<Work, Integer> {

    @Query("select " +
            "work.workId as workId, " +
            "work.worker.name as employeeName, " +
            "work.job.shop.shopName as shopName, " +
            "work.workDate as workDate, " +
            "work.job.beginTime as beginTime, " +
            "work.job.endTime as endTime, " +
            "work.checkin as checkin, " +
            "work.checkout as checkout, " +
            "work.score as score, " +
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
            "work.score as score, " +
            "work.salaryConfirmed as paid from Work work " +
            "where work.job.shop.company = ?1")
    Page<WorkProjection> findPageByCompanyOrderByWorkIdDesc(Company company, Pageable pageable);
}
