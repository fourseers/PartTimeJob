package com.fourseers.parttimejob.arrangement.repository;

import com.fourseers.parttimejob.common.entity.MerchantUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantUserRepository extends JpaRepository<MerchantUser, Integer> {

    MerchantUser findByUsername(String username);
}
