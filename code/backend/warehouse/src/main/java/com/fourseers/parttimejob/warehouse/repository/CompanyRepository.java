package com.fourseers.parttimejob.warehouse.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.fourseers.parttimejob.common.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Company findByCompanyName(String companyName);

    @Query("select merchantUser.company " +
           "from MerchantUser merchantUser " +
           "where merchantUser.username = ?1")
    Company findByManager(String username);
}
