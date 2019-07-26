package com.fourseers.parttimejob.billing.repository;

import com.fourseers.parttimejob.common.entity.Company;
import com.fourseers.parttimejob.common.entity.MonthlyBill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyBillRepository extends JpaRepository<MonthlyBill, Integer> {

    MonthlyBill findByCompanyAndYearAndMonth(Company company, Integer year, Integer month);

    MonthlyBill findByMonthlyBillId(String monthlyBillId);
}
