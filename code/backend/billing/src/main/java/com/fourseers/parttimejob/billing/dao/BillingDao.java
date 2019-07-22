package com.fourseers.parttimejob.billing.dao;

import com.fourseers.parttimejob.billing.projection.BillingProjection;
import com.fourseers.parttimejob.common.entity.Billing;
import org.springframework.data.domain.Page;

public interface BillingDao {

    Page<BillingProjection> getBillingsByCompanyIdOrderByBillIdDesc(Integer companyId, int pageCount, int pageSize);

    Billing findByBillId(Integer billId);

    void save(Billing billing);
}
