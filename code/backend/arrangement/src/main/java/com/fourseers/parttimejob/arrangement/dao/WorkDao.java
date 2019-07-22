package com.fourseers.parttimejob.arrangement.dao;

import com.fourseers.parttimejob.arrangement.projection.WorkProjection;
import com.fourseers.parttimejob.common.entity.Company;
import com.fourseers.parttimejob.common.entity.Shop;
import org.springframework.data.domain.Page;

public interface WorkDao {

    Page<WorkProjection> findPageByShop(Shop shop, int pageCount, int pageSize);

    Page<WorkProjection> findPageByCompany(Company company, int pageCount, int pageSize);
}
