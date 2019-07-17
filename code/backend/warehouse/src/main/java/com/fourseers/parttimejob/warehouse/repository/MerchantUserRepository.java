package com.fourseers.parttimejob.warehouse.repository;

import com.fourseers.parttimejob.common.entity.MerchantUser;
import com.fourseers.parttimejob.warehouse.projection.MerchantUserInfoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MerchantUserRepository extends JpaRepository<MerchantUser, Integer> {

    MerchantUser findByUserId(Integer userId);

    MerchantUser findByUsername(String username);

    @Query("select user.userId as userId, user.username as username, user.company.companyId as companyId, user.company.companyName as companyName from MerchantUser user")
    MerchantUserInfoProjection findBriefByUserId(Integer userId);
}
