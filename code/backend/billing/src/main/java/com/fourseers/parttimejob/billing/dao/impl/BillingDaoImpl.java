package com.fourseers.parttimejob.billing.dao.impl;

import com.fourseers.parttimejob.billing.dao.BillingDao;
import com.fourseers.parttimejob.billing.projection.BillingStatusProjection;
import com.fourseers.parttimejob.billing.repository.BillingRepository;
import com.fourseers.parttimejob.common.entity.Billing;
import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public void saveAll(List<Billing> billings) {
        billingRepository.saveAll(billings);
    }

    @Override
    public Double getBillingAmountByCompanyIdInGivenPeriod(Integer companyId, Date from, Date to) {
        return billingRepository.getBillingAmountByCompanyIdInGivenPeriod(companyId, from, to);
    }

    @Override
    public List<BillingStatusProjection> getBillingStatus(Integer companyId, Date from, Date to) {
        return billingRepository.getBillingStatusByCompanyIdInGivenPeriod(companyId, from, to);
    }

    @Override
    public Double getUserBalance(WechatUser user) {
        Double ret = billingRepository.getUserBalance(user);
        return ret == null ? 0.0 : ret;
    }

    @Override
    public Page<Billing> getBillingsDrawn(WechatUser user, Integer pageCount, Integer pageSize) {
        Pageable pageRequest = PageRequest.of(pageCount, pageSize);
        return billingRepository.getBillingsDrawn(user, pageRequest);
    }

    @Override
    public List<Billing> getBillingsToDraw(WechatUser user) {
        return billingRepository.getBillingsToDraw(user);
    }
}
