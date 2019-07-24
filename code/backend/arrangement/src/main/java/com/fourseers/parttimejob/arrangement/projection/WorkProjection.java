package com.fourseers.parttimejob.arrangement.projection;

public interface WorkProjection {

    Integer getWorkId();
    String getEmployeeName();
    String getShopName();
    String getWorkDate();
    String getBeginTime();
    String getEndTime();
    String getCheckin();
    String getCheckout();
    String getJobName();
    Integer getScore();
    Double getPayment();
    Boolean getPaid();
}
