package com.fourseers.parttimejob.warehouse.service;

import com.fourseers.parttimejob.warehouse.entity.Company;

public interface CompanyService {

    void save(Company company, int bossId);

    Company findByCompanyName(String companyName);
}
