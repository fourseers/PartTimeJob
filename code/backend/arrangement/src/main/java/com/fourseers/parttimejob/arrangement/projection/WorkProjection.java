package com.fourseers.parttimejob.arrangement.projection;

public interface WorkProjection {

    String getEmployeeName();
    String getShopName();
    String getWorkDate();
    String getBeginTime();
    String getEndTime();
    String getCheckin();
    String getCheckout();
    Integer getScore();
    Boolean getPaid();
}
