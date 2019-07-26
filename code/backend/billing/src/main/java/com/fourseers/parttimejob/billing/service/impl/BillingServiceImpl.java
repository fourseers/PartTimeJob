package com.fourseers.parttimejob.billing.service.impl;

import com.fourseers.parttimejob.billing.dao.BillingDao;
import com.fourseers.parttimejob.billing.dao.CompanyDao;
import com.fourseers.parttimejob.billing.dao.MonthlyBillDao;
import com.fourseers.parttimejob.billing.dao.WorkDao;
import com.fourseers.parttimejob.billing.projection.WorkBillingProjection;
import com.fourseers.parttimejob.billing.service.BillingService;
import com.fourseers.parttimejob.common.entity.Billing;
import com.fourseers.parttimejob.common.entity.Company;
import com.fourseers.parttimejob.common.entity.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Service
@Transactional
public class BillingServiceImpl implements BillingService {

    @Autowired
    WorkDao workDao;

    @Autowired
    BillingDao billingDao;

    @Autowired
    CompanyDao companyDao;

    @Autowired
    MonthlyBillDao monthlyBillDao;

    public Page<WorkBillingProjection> getBillingsByUsernameOrderByBillIdDescInGivenPeriod(String username, Date fromDate, Date toDate, int pageCount, int pageSize) {

        Company company = companyDao.findByUsername(username);

        if (company == null) {
            throw new RuntimeException("user does not belong to any company");
        }

        return workDao.getBillingsByCompanyIdOrderByBillIdDescInGivenPeriod(company.getCompanyId(), fromDate, toDate, pageCount, pageSize);
    }

    public void payBill(String username, Integer workId, Billing billing) {
        Company company = companyDao.findByUsername(username);

        if (company == null) {
            throw new RuntimeException("user does not belong to any company");
        }

        Work work = workDao.findByWorkId(workId);

        if (work == null || !work.getJob().getShop().getCompany().equals(company)) {
            throw new RuntimeException("work not exist or not belong to current company");
        } else if (work.getSalaryConfirmed()) {
            throw new RuntimeException("work already paid");
        }
        billing.setWork(work);
        billingDao.save(billing);
        work.setBilling(billing);
        work.setSalaryConfirmed(true);
        workDao.save(work);
    }

    public Double getBillingAmountByUsernameInGivenPeriod(String username, Date from, Date to) {

        if (from.after(to)) {
            throw new RuntimeException("incorrect param");
        }

        Company company = companyDao.findByUsername(username);

        if (company == null) {
            throw new RuntimeException("user does not belong to any company");
        }

        Double result = billingDao.getBillingAmountByCompanyIdInGivenPeriod(company.getCompanyId(), from, to);
        if (result != null) {
            return result;
        } else {
            return (double) 0;
        }
    }

}
