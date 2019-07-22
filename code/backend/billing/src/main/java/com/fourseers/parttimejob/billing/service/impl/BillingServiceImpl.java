package com.fourseers.parttimejob.billing.service.impl;

import com.fourseers.parttimejob.billing.dao.BillingDao;
import com.fourseers.parttimejob.billing.dao.CompanyDao;
import com.fourseers.parttimejob.billing.projection.BillingProjection;
import com.fourseers.parttimejob.billing.service.BillingService;
import com.fourseers.parttimejob.common.entity.Billing;
import com.fourseers.parttimejob.common.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BillingServiceImpl implements BillingService {

    @Autowired
    BillingDao billingDao;

    @Autowired
    CompanyDao companyDao;

    public Page<BillingProjection> getBillingsByUsernameOrderByBillIdDesc(String username, int pageCount, int pageSize) {

        Company company = companyDao.findByUsername(username);

        if (company == null) {
            throw new RuntimeException("user does not belong to any company");
        }

        return billingDao.getBillingsByCompanyIdOrderByBillIdDesc(company.getCompanyId(), pageCount, pageSize);
    }

    public void payBill(String username, Integer billId) {
        Company company = companyDao.findByUsername(username);

        if (company == null) {
            throw new RuntimeException("user does not belong to any company");
        }

        Billing billing = billingDao.findByBillId(billId);

        if (billing == null || !billing.getShop().getCompany().equals(company)) {
            throw new RuntimeException("bill not exist or not belong to current company");
        } else if (billing.getPaid()) {
            throw new RuntimeException("bill already paid");
        }
        billing.setPaid(true);
        billingDao.save(billing);
    }

}
