package com.fourseers.parttimejob.auth.repository;

import com.fourseers.parttimejob.auth.entity.MerchantUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantUserRepository extends JpaRepository<MerchantUser, Integer> {

    MerchantUser findByUsername(String username);
}
