package com.fourseers.parttimejob.billing.service.impl;

import com.fourseers.parttimejob.billing.dao.BillingDao;
import com.fourseers.parttimejob.billing.dao.CompanyDao;
import com.fourseers.parttimejob.billing.dao.MonthlyBillDao;
import com.fourseers.parttimejob.billing.dao.WorkDao;
import com.fourseers.parttimejob.billing.projection.BillingStatusProjection;
import com.fourseers.parttimejob.billing.projection.UserWorkEntryProjection;
import com.fourseers.parttimejob.billing.projection.WorkBillingProjection;
import com.fourseers.parttimejob.billing.service.BillingService;
import com.fourseers.parttimejob.billing.service.PayService;
import com.fourseers.parttimejob.common.entity.Billing;
import com.fourseers.parttimejob.common.entity.Company;
import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.common.entity.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

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

    @Autowired
    @Qualifier("mockPaymentService") PayService payService;

    @Value("${app.pagination.pageSize}")
    private Integer DEFAULT_PAGE_SIZE;

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
            return 0.0;
        }
    }

    @Override
    public List<BillingStatusProjection> getBillingStatus(String username, Integer fromYear, Integer fromMonth, Integer toYear, Integer toMonth) {

        Company company = companyDao.findByUsername(username);

        if (company == null) {
            throw new RuntimeException("user does not belong to any company");
        }

        Calendar aCalendar = Calendar.getInstance();
        aCalendar.set(Calendar.YEAR, fromYear);
        aCalendar.set(Calendar.MONTH, fromMonth - 1);
        aCalendar.set(Calendar.DAY_OF_MONTH, 1);
        Date from = new Date(aCalendar.getTime().getTime());
        aCalendar.set(Calendar.YEAR, toYear);
        aCalendar.set(Calendar.MONTH, toMonth - 1);
        aCalendar.set(Calendar.DAY_OF_MONTH, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date to = new Date(aCalendar.getTime().getTime());
        if (from.after(to)) {
            throw new RuntimeException("incorrect param");
        }

        return billingDao.getBillingStatus(company.getCompanyId(), from, to);

    }

    @Override
    public Page<UserWorkEntryProjection> getUserWork(WechatUser wechatUser, Integer pageCount) {
        return workDao.getUserWorkAndBill(wechatUser, pageCount, DEFAULT_PAGE_SIZE);
    }

    @Override
    public Double drawAllMoney(WechatUser wechatUser) {
        List<Billing> bills =  billingDao.getBillingsToDraw(wechatUser);

        double payment = 0;
        for(Billing b: bills) {
            payment += b.getPayment();
        }
        // use mock payment client to generate method and meta
        PayService.PaymentResult result = payService.pay(wechatUser, payment);
        if(result.isSuccess()) {
            // set all method and meta in billing table
            for(Billing b: bills) {
                b.setMethod(payService.getMethod());
                b.setMeta(result.getMeta());
            }
            billingDao.saveAll(bills);
            return payment;
        }
        return 0.0;
    }

    /**
     * Get user balance by summing all billing entries in database.
     * @param user wechat user to calculate balance
     * @return user balance
     */
    @Override
    public Double getUserBalance(WechatUser user) {
        return billingDao.getUserBalance(user);
    }

    @Override
    public Page<Billing> getDrawnBillings(WechatUser user, int pageCount) {
        return billingDao.getBillingsDrawn(user, pageCount, DEFAULT_PAGE_SIZE);
    }

}
