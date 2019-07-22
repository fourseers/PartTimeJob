package com.fourseers.parttimejob.billing.dto;

import javax.validation.constraints.NotNull;

public class BillDto {
    private Integer billId;

    @NotNull
    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }
}
