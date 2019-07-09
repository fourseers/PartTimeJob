package com.fourseers.parttimejob.warehouse.service.impl;

import com.fourseers.parttimejob.warehouse.dao.CompanyDao;
import com.fourseers.parttimejob.warehouse.dao.MerchantUserDao;
import com.fourseers.parttimejob.warehouse.entity.Company;
import com.fourseers.parttimejob.warehouse.entity.MerchantUser;
import com.fourseers.parttimejob.warehouse.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private MerchantUserDao merchantUserDao;

    public void save(Company company, int bossId) {
        MerchantUser boss = merchantUserDao.findByUserId(bossId);
        company.setBoss(boss);
        companyDao.save(company);
        boss.setCompany(company);
        merchantUserDao.save(boss);
    }

    public void save(Company company, String bossName) {
        MerchantUser boss = merchantUserDao.findByUsername(bossName);
        company.setBoss(boss);
        companyDao.save(company);
        boss.setCompany(company);
        merchantUserDao.save(boss);
    }

    public Company findByCompanyName(String companyName) {
        return companyDao.findByCompanyName(companyName);
    }
}
