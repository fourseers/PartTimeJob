package com.fourseers.parttimejob.arrangement.dao;

import com.fourseers.parttimejob.arrangement.projection.WorkProjection;
import com.fourseers.parttimejob.arrangement.projection.WorkStatusProjection;
import com.fourseers.parttimejob.common.entity.*;
import org.springframework.data.domain.Page;

import java.sql.Date;

public interface WorkDao {

    Page<WorkProjection> findPageByShop(Shop shop, int pageCount, int pageSize);

    Page<WorkProjection> findPageByCompany(Company company, int pageCount, int pageSize);

    Work findById(int workId);

    Work findTodayByUserAndJob(WechatUser user, Job job);

    void save(Work work);

    WorkStatusProjection getWorkStatus(Integer shopId, Date from, Date to);
}
