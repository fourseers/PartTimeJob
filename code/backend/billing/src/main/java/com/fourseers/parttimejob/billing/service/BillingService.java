package com.fourseers.parttimejob.billing.service;

import com.fourseers.parttimejob.billing.projection.BillingStatusProjection;
import com.fourseers.parttimejob.billing.projection.UserWorkEntryProjection;
import com.fourseers.parttimejob.billing.projection.WorkBillingProjection;
import com.fourseers.parttimejob.common.entity.Billing;
import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.data.domain.Page;

import java.sql.Date;
import java.util.List;

public interface BillingService {

    Page<WorkBillingProjection> getBillingsByUsernameOrderByBillIdDescInGivenPeriod(String username, Date fromDate, Date toDate, int pageCount, int pageSize);

    void payBill(String username, Integer billId, Billing billing);

    Double getBillingAmountByUsernameInGivenPeriod(String username, Date from, Date to);

    List<BillingStatusProjection> getBillingStatus(String username, Integer fromYear, Integer fromMonth, Integer toYear, Integer toMonth);

    Page<UserWorkEntryProjection> getUserWork(WechatUser wechatUser, Integer pageCount);

    Double drawAllMoney(WechatUser wechatUser);

    Double getUserBalance(WechatUser user);

    Page<Billing> getDrawnBillings(WechatUser user, int pageCount);
}
