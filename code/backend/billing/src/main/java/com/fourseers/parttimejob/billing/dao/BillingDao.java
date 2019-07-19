package com.fourseers.parttimejob.billing.dao;

import com.fourseers.parttimejob.billing.projection.BillingProjection;
import org.springframework.data.domain.Page;

public interface BillingDao {

    Page<BillingProjection> getBillingsByCompanyIdOrderByBillIdDesc(Integer companyId, int pageCount, int pageSize);
}
