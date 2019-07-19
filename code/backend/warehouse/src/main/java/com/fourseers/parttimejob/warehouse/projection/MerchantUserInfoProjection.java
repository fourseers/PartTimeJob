package com.fourseers.parttimejob.warehouse.projection;

public interface MerchantUserInfoProjection {

    Integer getUserId();

    String getUsername();

    Boolean getBanned();

    Integer getCompanyId();

    String getCompanyName();
}
