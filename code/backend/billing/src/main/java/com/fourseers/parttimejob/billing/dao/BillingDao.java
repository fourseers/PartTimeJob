package com.fourseers.parttimejob.billing.dao;

import com.fourseers.parttimejob.common.entity.Billing;

import java.sql.Date;

public interface BillingDao {

    void save(Billing billing);

    Double getBillingAmountByCompanyIdInGivenPeriod(Integer companyId, Date from, Date to);
}
