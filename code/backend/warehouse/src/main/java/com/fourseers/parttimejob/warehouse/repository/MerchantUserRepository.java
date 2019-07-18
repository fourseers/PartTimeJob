package com.fourseers.parttimejob.warehouse.repository;

import com.fourseers.parttimejob.common.entity.MerchantUser;
import com.fourseers.parttimejob.warehouse.projection.MerchantUserInfoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MerchantUserRepository extends JpaRepository<MerchantUser, Integer> {

    MerchantUser findByUserId(Integer userId);

    MerchantUser findByUsername(String username);

    @Query("select user.userId as userId, user.username as username, user.banned as banned, company.companyId as companyId, company.companyName as companyName from MerchantUser user left join user.company as company where user.userId = ?1")
    MerchantUserInfoProjection findBriefByUserId(Integer userId);

    @Query("select user.userId as userId, user.username as username, user.banned as banned, company.companyId as companyId, company.companyName as companyName from MerchantUser user left join user.company as company order by user.userId")
    Page<MerchantUserInfoProjection> findAllOrderByUserId(Pageable pageable);
}
