package com.fourseers.parttimejob.arrangement.dao.impl;

import com.fourseers.parttimejob.arrangement.dao.WorkDao;
import com.fourseers.parttimejob.arrangement.projection.WorkProjection;
import com.fourseers.parttimejob.arrangement.repository.WorkRepository;
import com.fourseers.parttimejob.common.entity.Company;
import com.fourseers.parttimejob.common.entity.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class WorkDaoImpl implements WorkDao {

    @Autowired
    private WorkRepository workRepository;

    public Page<WorkProjection> findPageByShop(Shop shop, int pageCount, int pageSize) {
        Pageable pageable = PageRequest.of(pageCount, pageSize);
        return workRepository.findPageByShopOrderByWorkIdDesc(shop, pageable);
    }

    public Page<WorkProjection> findPageByCompany(Company company, int pageCount, int pageSize) {
        Pageable pageable = PageRequest.of(pageCount, pageSize);
        return workRepository.findPageByCompanyOrderByWorkIdDesc(company, pageable);
    }
}
