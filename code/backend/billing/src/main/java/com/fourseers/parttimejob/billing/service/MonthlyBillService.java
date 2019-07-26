package com.fourseers.parttimejob.billing.service;

import com.fourseers.parttimejob.common.entity.MonthlyBill;

public interface MonthlyBillService {

    String monthlyPayBill(String username, Integer year, Integer month);

    MonthlyBill findByMonthlyBillId(String monthlyBillId);

    void save(MonthlyBill monthlyBill);
}
