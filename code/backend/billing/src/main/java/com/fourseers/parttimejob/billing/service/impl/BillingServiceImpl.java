package com.fourseers.parttimejob.billing.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.fourseers.parttimejob.billing.dao.BillingDao;
import com.fourseers.parttimejob.billing.dao.CompanyDao;
import com.fourseers.parttimejob.billing.dao.WorkDao;
import com.fourseers.parttimejob.billing.projection.WorkBillingProjection;
import com.fourseers.parttimejob.billing.service.BillingService;
import com.fourseers.parttimejob.common.entity.Billing;
import com.fourseers.parttimejob.common.entity.Company;
import com.fourseers.parttimejob.common.entity.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

@Service
@Transactional
public class BillingServiceImpl implements BillingService {

    @Autowired
    WorkDao workDao;

    @Autowired
    BillingDao billingDao;

    @Autowired
    CompanyDao companyDao;

    @Value("${app.alipay_app_id}")
    String alipayAppId;

    @Value("${app.alipay_merchant_private_key}")
    String alipayMerchantPrivateKey;

    @Value("${app.alipay_public_key}")
    String alipayPublicKey;

    @Value("${app.alipay_notify_url}")
    String alipayNotifyUrl;

    @Value("${app.alipay_return_url}")
    String alipayReturnUrl;

    @Value("${app.alipay_gateway_url}")
    String alipayGatewayUrl;

    public Page<WorkBillingProjection> getBillingsByUsernameOrderByBillIdDesc(String username, int pageCount, int pageSize) {

        Company company = companyDao.findByUsername(username);

        if (company == null) {
            throw new RuntimeException("user does not belong to any company");
        }

        return workDao.getBillingsByCompanyIdOrderByBillIdDesc(company.getCompanyId(), pageCount, pageSize);
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

    @Override
    public String monthlyPayBill(String username, Integer year, Integer month) {

        Company company = companyDao.findByUsername(username);

        if (company == null) {
            throw new RuntimeException("user does not belong to any company");
        }

        Calendar aCalendar = Calendar.getInstance();
        aCalendar.set(Calendar.YEAR, year);
        aCalendar.set(Calendar.MONTH, month - 1);
        aCalendar.set(Calendar.DAY_OF_MONTH, 1);

        Date from = new Date(aCalendar.getTime().getTime());

        aCalendar.set(Calendar.DAY_OF_MONTH, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date to = new Date(aCalendar.getTime().getTime());

        Double amount = billingDao.getBillingAmountByCompanyIdInGivenPeriod(company.getCompanyId(), from, to);
        if (amount == null) {
            throw new RuntimeException("nothing to pay");
        }

        AlipayClient alipayClient = new DefaultAlipayClient(alipayGatewayUrl, alipayAppId, alipayMerchantPrivateKey, "json", "utf-8", alipayPublicKey, "RSA2");

        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(alipayReturnUrl);
        alipayRequest.setNotifyUrl(alipayNotifyUrl);

        String tradeNo = String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()) + company.getCompanyId();
        String amountStr = String.valueOf(amount);
        String subject = "灵活用工月结账单";

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + tradeNo + "\","
                + "\"total_amount\":\"" + amountStr + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        try {
            AlipayTradePagePayResponse response = alipayClient.pageExecute(alipayRequest);
            if (!response.isSuccess()) {
                throw new RuntimeException("unhandled exception");
            }
            return response.getBody();
        } catch (AlipayApiException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

}
