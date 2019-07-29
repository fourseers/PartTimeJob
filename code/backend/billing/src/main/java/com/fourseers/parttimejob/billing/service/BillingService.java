package com.fourseers.parttimejob.billing.service;

import com.fourseers.parttimejob.billing.projection.WorkBillingProjection;
import com.fourseers.parttimejob.common.entity.Billing;
import org.springframework.data.domain.Page;

import java.sql.Date;

public interface BillingService {

    Page<WorkBillingProjection> getBillingsByUsernameOrderByBillIdDescInGivenPeriod(String username, Date fromDate, Date toDate, int pageCount, int pageSize);

    void payBill(String username, Integer billId, Billing billing);

    Double getBillingAmountByUsernameInGivenPeriod(String username, Date from, Date to);
}
