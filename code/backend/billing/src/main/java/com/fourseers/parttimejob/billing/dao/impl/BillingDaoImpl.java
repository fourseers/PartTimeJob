package com.fourseers.parttimejob.billing.dao.impl;

import com.fourseers.parttimejob.billing.dao.BillingDao;
import com.fourseers.parttimejob.billing.projection.BillingStatusProjection;
import com.fourseers.parttimejob.billing.repository.BillingRepository;
import com.fourseers.parttimejob.common.entity.Billing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class BillingDaoImpl implements BillingDao {

    @Autowired
    private BillingRepository billingRepository;

    @Override
    public void save(Billing billing) {
        billingRepository.save(billing);
    }

    @Override
    public Double getBillingAmountByCompanyIdInGivenPeriod(Integer companyId, Date from, Date to) {
        return billingRepository.getBillingAmountByCompanyIdInGivenPeriod(companyId, from, to);
    }

    @Override
    public List<BillingStatusProjection> getBillingStatus(Integer companyId, Date from, Date to) {
        return billingRepository.getBillingStatusByCompanyIdInGivenPeriod(companyId, from, to);
    }
}
