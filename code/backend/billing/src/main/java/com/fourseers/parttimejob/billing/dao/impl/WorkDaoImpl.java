package com.fourseers.parttimejob.billing.dao.impl;

import com.fourseers.parttimejob.billing.dao.WorkDao;
import com.fourseers.parttimejob.billing.projection.UserWorkEntryProjection;
import com.fourseers.parttimejob.billing.projection.WorkBillingProjection;
import com.fourseers.parttimejob.billing.repository.WorkRepository;
import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.common.entity.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public class WorkDaoImpl implements WorkDao {

    @Autowired
    WorkRepository workRepository;

    @Override
    public Page<WorkBillingProjection> getBillingsByCompanyIdOrderByBillIdDescInGivenPeriod(Integer companyId, Date fromDate, Date toDate, int pageCount, int pageSize) {
        Pageable pageable = PageRequest.of(pageCount, pageSize);

        return workRepository.getBillingsByCompanyIdOrderByBillIdDescInGivenPeriod(companyId, fromDate, toDate, pageable);
    }

    @Override
    public Work findByWorkId(Integer workId) {
        return workRepository.findByWorkId(workId);
    }

    @Override
    public void save(Work work) {
        workRepository.save(work);
    }

    @Override
    public Page<UserWorkEntryProjection> getUserWorkAndBill(WechatUser user, Integer pageCount, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageCount, pageSize);
        Page<UserWorkEntryProjection> ret = workRepository.getUserWorkEntries(user, pageable);
        return ret;
    }
}
