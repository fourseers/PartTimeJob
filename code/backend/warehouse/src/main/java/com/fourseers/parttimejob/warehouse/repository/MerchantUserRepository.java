package com.fourseers.parttimejob.warehouse.repository;

import com.fourseers.parttimejob.common.entity.MerchantUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantUserRepository extends JpaRepository<MerchantUser, Integer> {

    MerchantUser findByUserId(Integer userId);

    MerchantUser findByUsername(String username);
}
