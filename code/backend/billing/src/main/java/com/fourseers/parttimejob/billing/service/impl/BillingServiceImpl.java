package com.fourseers.parttimejob.billing.service.impl;

import com.fourseers.parttimejob.billing.dao.BillingDao;
import com.fourseers.parttimejob.billing.dao.CompanyDao;
import com.fourseers.parttimejob.billing.projection.BillingProjection;
import com.fourseers.parttimejob.billing.service.BillingService;
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

}
