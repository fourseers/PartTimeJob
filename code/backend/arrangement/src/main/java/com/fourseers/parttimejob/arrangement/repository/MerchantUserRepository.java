package com.fourseers.parttimejob.arrangement.repository;

import com.fourseers.parttimejob.arrangement.entity.MerchantUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantUserRepository extends JpaRepository<MerchantUser, Integer> {

    MerchantUser findByUsername(String username);
}
