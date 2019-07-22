package com.fourseers.parttimejob.billing.dao.impl;

import com.fourseers.parttimejob.billing.dao.BillingDao;
import com.fourseers.parttimejob.billing.repository.BillingRepository;
import com.fourseers.parttimejob.common.entity.Billing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BillingDaoImpl implements BillingDao {

    @Autowired
    private BillingRepository billingRepository;

    @Override
    public void save(Billing billing) {
        billingRepository.save(billing);
    }
}
