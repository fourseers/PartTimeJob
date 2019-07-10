package com.fourseers.parttimejob.warehouse.repository;

import com.fourseers.parttimejob.warehouse.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Company findByCompanyName(String companyName);
}