package com.fourseers.parttimejob.billing.dao;

import com.fourseers.parttimejob.common.entity.Company;

public interface CompanyDao {

    Company findByUsername(String username);
}
