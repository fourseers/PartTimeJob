package com.fourseers.parttimejob.billing.dao.impl;

import com.fourseers.parttimejob.billing.dao.CompanyDao;
import com.fourseers.parttimejob.billing.repository.CompanyRepository;
import com.fourseers.parttimejob.common.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyDaoImpl implements CompanyDao {

    @Autowired
    CompanyRepository companyRepository;

    public Company findByUsername(String username) {
        return companyRepository.findByManager(username);
    }
}
