package com.fourseers.parttimejob.billing.projection;

import java.sql.Date;
import java.sql.Time;

public interface WorkBillingProjection {

    Integer getBillId();
    Integer getShopId();
    String getShopName();
    Integer getEmployeeId();
    String getEmployeeName();
    Date getWorkDate();
    Time getBeginTime();
    Time getEndTime();
    Double getPayment();
    String getJobName();
    Boolean getPaid();
}
