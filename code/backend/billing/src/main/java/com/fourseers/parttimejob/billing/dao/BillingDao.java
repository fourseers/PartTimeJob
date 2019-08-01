package com.fourseers.parttimejob.billing.dao;

import com.fourseers.parttimejob.billing.projection.BillingStatusProjection;
import com.fourseers.parttimejob.common.entity.Billing;

import java.sql.Date;
import java.util.List;

public interface BillingDao {

    void save(Billing billing);

    Double getBillingAmountByCompanyIdInGivenPeriod(Integer companyId, Date from, Date to);

    List<BillingStatusProjection> getBillingStatus(Integer companyId, Date from, Date to);
}
