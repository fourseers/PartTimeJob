package com.fourseers.parttimejob.billing.dao.impl;

import com.fourseers.parttimejob.billing.dao.WorkDao;
import com.fourseers.parttimejob.billing.projection.WorkBillingProjection;
import com.fourseers.parttimejob.billing.repository.WorkRepository;
import com.fourseers.parttimejob.common.entity.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class WorkDaoImpl implements WorkDao {

    @Autowired
    WorkRepository workRepository;

    @Override
    public Page<WorkBillingProjection> getBillingsByCompanyIdOrderByBillIdDesc(Integer companyId, int pageCount, int pageSize) {
        Pageable pageable = PageRequest.of(pageCount, pageSize);

        return workRepository.getBillingsByCompanyIdOrderByBillIdDesc(companyId, pageable);
    }

    @Override
    public Work findByWorkId(Integer workId) {
        return workRepository.findByWorkId(workId);
    }

    @Override
    public void save(Work work) {
        workRepository.save(work);
    }
}
