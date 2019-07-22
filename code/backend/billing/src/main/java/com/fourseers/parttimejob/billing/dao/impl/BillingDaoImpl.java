package com.fourseers.parttimejob.billing.dao.impl;

import com.fourseers.parttimejob.billing.dao.BillingDao;
import com.fourseers.parttimejob.billing.projection.BillingProjection;
import com.fourseers.parttimejob.billing.repository.BillingRepository;
import com.fourseers.parttimejob.common.entity.Billing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class BillingDaoImpl implements BillingDao {

    @Autowired
    BillingRepository billingRepository;

    @Override
    public Page<BillingProjection> getBillingsByCompanyIdOrderByBillIdDesc(Integer companyId, int pageCount, int pageSize) {
        Pageable pageable = PageRequest.of(pageCount, pageSize);

        return billingRepository.getBillingsByCompanyIdOrderByBillIdDesc(companyId, pageable);
    }

    @Override
    public Billing findByBillId(Integer billId) {
        return billingRepository.findByBillId(billId);
    }

    @Override
    public void save(Billing billing) {
        billingRepository.save(billing);
    }
}
