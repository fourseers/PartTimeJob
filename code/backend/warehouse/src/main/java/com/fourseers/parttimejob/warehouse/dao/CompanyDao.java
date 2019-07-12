package com.fourseers.parttimejob.warehouse.dao;

import com.fourseers.parttimejob.warehouse.entity.Company;

public interface CompanyDao {

    void save(Company company);

    Company findByCompanyName(String companyName);

    Company findByUsername(String username);
}
