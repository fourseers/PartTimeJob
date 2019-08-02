package com.fourseers.parttimejob.billing.projection;

import java.sql.Date;

public interface UserWorkEntryProjection {
    Integer getWorkId();
    Date getWorkDate();
    Boolean getSalaryConfirmed();
    Boolean getRejected();
    Integer getJobId();
    String getJobName();
    Integer getBillId();
    Double getPayment();
}
