package com.fourseers.parttimejob.billing.service;

import com.fourseers.parttimejob.billing.projection.BillingProjection;
import org.springframework.data.domain.Page;

public interface BillingService {

    Page<BillingProjection> getBillingsByUsernameOrderByBillIdDesc(String username, int pageCount, int pageSize);

    void payBill(String username, Integer billId);
}
