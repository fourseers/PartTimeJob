package com.fourseers.parttimejob.billing.projection;

public interface BillingStatusProjection {
    Integer getShopId();
    String getShopName();
    Double getPayment();
}
