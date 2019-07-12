package com.fourseers.parttimejob.warehouse.repository;

import com.fourseers.parttimejob.warehouse.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Company findByCompanyName(String companyName);

    @Query("select merchantUser.company " +
           "from MerchantUser merchantUser " +
           "where merchantUser.username = ?1")
    Company findByManager(String username);
}
