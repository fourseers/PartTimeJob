package com.fourseers.parttimejob.billing.projection;

import java.sql.Timestamp;

public interface BillingProjection {

    Integer getBillId();
    Integer getShopId();
    String getShopName();
    Integer getEmployeeId();
    String getEmployeeName();
    Timestamp getBeginTime();
    Timestamp getEndTime();
    String getJobName();
    Double getPayment();
    Boolean getPaid();
}