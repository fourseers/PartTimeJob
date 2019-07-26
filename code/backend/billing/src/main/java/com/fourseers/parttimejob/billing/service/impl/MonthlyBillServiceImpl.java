package com.fourseers.parttimejob.billing.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.fourseers.parttimejob.billing.dao.BillingDao;
import com.fourseers.parttimejob.billing.dao.CompanyDao;
import com.fourseers.parttimejob.billing.dao.MonthlyBillDao;
import com.fourseers.parttimejob.billing.service.MonthlyBillService;
import com.fourseers.parttimejob.common.entity.Company;
import com.fourseers.parttimejob.common.entity.MonthlyBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;

@Service
@Transactional
public class MonthlyBillServiceImpl implements MonthlyBillService {

    @Autowired
    private MonthlyBillDao monthlyBillDao;

    @Autowired
    private BillingDao billingDao;

    @Autowired
    private CompanyDao companyDao;

    @Value("${app.alipay_app_id}")
    private String ALIPAY_APP_ID;

    @Value("${app.alipay_merchant_private_key}")
    private String ALIPAY_MERCHANT_PRIVATE_KEY;

    @Value("${app.alipay_public_key}")
    private String ALIPAY_PUBLIC_KEY;

    @Value("${app.alipay_notify_url}")
    private String ALIPAY_NOTIFY_URL;

    @Value("${app.alipay_return_url}")
    private String ALIPAY_RETURN_URL;

    @Value("${app.alipay_gateway_url}")
    private String ALIPAY_GATEWAY_URL;

    @Override
    public String monthlyPayBill(String username, Integer year, Integer month) {

        Company company = companyDao.findByUsername(username);

        if (company == null) {
            throw new RuntimeException("user does not belong to any company");
        }

        MonthlyBill monthlyBill = monthlyBillDao.findByCompanyAndYearAndMonth(company, year, month);
        if (monthlyBill != null && monthlyBill.getStatus().equals("paid")) {
            throw new RuntimeException("already paid");
        } else if (monthlyBill == null) {
            Random random = new Random();
            String tradeNo = new Timestamp(System.currentTimeMillis()).getTime() + String.format("%06d", random.nextInt(100000));

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

            monthlyBill = new MonthlyBill();
            monthlyBill.setCompany(company);
            monthlyBill.setMethod("支付宝");
            monthlyBill.setYear(year);
            monthlyBill.setMonth(month);
            monthlyBill.setMeta(tradeNo);
            monthlyBill.setAmount(amount);
            monthlyBill.setStatus("pending");
        }

        AlipayClient alipayClient = new DefaultAlipayClient(ALIPAY_GATEWAY_URL, ALIPAY_APP_ID, ALIPAY_MERCHANT_PRIVATE_KEY, "json", "utf-8", ALIPAY_PUBLIC_KEY, "RSA2");

        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(ALIPAY_RETURN_URL);
        alipayRequest.setNotifyUrl(ALIPAY_NOTIFY_URL);

        String tradeNo = monthlyBill.getMeta();
        String amountStr = String.valueOf(monthlyBill.getAmount());
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

            monthlyBillDao.save(monthlyBill);
            return response.getBody();
        } catch (AlipayApiException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public MonthlyBill findByMonthlyBillId(String monthlyBillId) {
        return monthlyBillDao.findByMonthlyBillId(monthlyBillId);
    }

    public void save(MonthlyBill monthlyBill) {
        monthlyBillDao.save(monthlyBill);
    }
}
