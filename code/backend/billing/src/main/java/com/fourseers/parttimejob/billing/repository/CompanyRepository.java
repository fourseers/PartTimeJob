package com.fourseers.parttimejob.billing.repository;

import com.fourseers.parttimejob.common.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    @Query("select merchantUser.company " +
            "from MerchantUser merchantUser " +
            "where merchantUser.username = ?1")
    Company findByManager(String username);
}
