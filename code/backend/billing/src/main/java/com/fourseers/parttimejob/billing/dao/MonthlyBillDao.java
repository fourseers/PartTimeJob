package com.fourseers.parttimejob.billing.dao;

import com.fourseers.parttimejob.common.entity.Company;
import com.fourseers.parttimejob.common.entity.MonthlyBill;

public interface MonthlyBillDao {

    MonthlyBill findByCompanyAndYearAndMonth(Company company, Integer year, Integer month);

    MonthlyBill findByMonthlyBillId(String monthlyBillId);

    void save(MonthlyBill monthlyBill);
}
