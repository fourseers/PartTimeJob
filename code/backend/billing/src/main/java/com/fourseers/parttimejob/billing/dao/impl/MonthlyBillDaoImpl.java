package com.fourseers.parttimejob.billing.dao.impl;

import com.fourseers.parttimejob.billing.dao.MonthlyBillDao;
import com.fourseers.parttimejob.billing.repository.MonthlyBillRepository;
import com.fourseers.parttimejob.common.entity.Company;
import com.fourseers.parttimejob.common.entity.MonthlyBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MonthlyBillDaoImpl implements MonthlyBillDao {

    @Autowired
    private MonthlyBillRepository monthlyBillRepository;

    public MonthlyBill findByCompanyAndYearAndMonth(Company company, Integer year, Integer month) {
        return monthlyBillRepository.findByCompanyAndYearAndMonth(company, year, month);
    }

    public MonthlyBill findByMonthlyBillId(String monthlyBillId) {
        return monthlyBillRepository.findByMonthlyBillId(monthlyBillId);
    }

    @Override
    public void save(MonthlyBill monthlyBill) {
        monthlyBillRepository.save(monthlyBill);
    }
}
