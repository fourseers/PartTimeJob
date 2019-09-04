package com.fourseers.parttimejob.billing.dto;

public class DrawMoneyDto {
    boolean success;
    Double payment;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }
}
