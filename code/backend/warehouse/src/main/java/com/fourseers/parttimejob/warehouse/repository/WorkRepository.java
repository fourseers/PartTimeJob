package com.fourseers.parttimejob.warehouse.repository;

import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.common.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Integer> {

    @Query(" FROM Work w where w.worker = ?1 and w.job.shop.shopId = ?2")
    List<Work> getByWorkerAndShop(WechatUser user, Integer shopId);
}
