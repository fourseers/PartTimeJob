package com.fourseers.parttimejob.warehouse.dao.impl;

import com.fourseers.parttimejob.warehouse.Repository.CompanyRepository;
import com.fourseers.parttimejob.warehouse.dao.CompanyDao;
import com.fourseers.parttimejob.warehouse.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyDaoImpl implements CompanyDao {

    @Autowired
    private CompanyRepository companyRepository;

    public void save(Company company) {
        companyRepository.save(company);
    }

    public Company findByCompanyName(String companyName) {
        return companyRepository.findByCompanyName(companyName);
    }
}
