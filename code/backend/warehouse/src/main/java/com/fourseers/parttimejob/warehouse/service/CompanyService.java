package com.fourseers.parttimejob.warehouse.service;

import com.fourseers.parttimejob.common.entity.Company;

public interface CompanyService {

    void save(Company company, int bossId);

    void save(Company company, String bossName);

    Company findByCompanyName(String companyName);

    Company findByUsername(String username);
}
