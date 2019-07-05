package com.fourseers.parttimejob.warehouse.service.impl;

import com.fourseers.parttimejob.warehouse.dao.CompanyDao;
import com.fourseers.parttimejob.warehouse.entity.Company;
import com.fourseers.parttimejob.warehouse.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    public void save(Company company) {
        companyDao.save(company);
    }

    public Company findByCompanyName(String companyName) {
        return companyDao.findByCompanyName(companyName);
    }
}
