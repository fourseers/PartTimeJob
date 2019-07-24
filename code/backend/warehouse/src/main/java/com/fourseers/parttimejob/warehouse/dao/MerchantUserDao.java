package com.fourseers.parttimejob.warehouse.dao;

import com.fourseers.parttimejob.common.entity.MerchantUser;
import com.fourseers.parttimejob.warehouse.projection.MerchantUserInfoProjection;
import org.springframework.data.domain.Page;

public interface MerchantUserDao {

    void save(MerchantUser merchantUser);

    MerchantUser findByUserId(Integer userId);

    MerchantUser findByUsername(String username);

    MerchantUserInfoProjection findBriefByUserId(Integer userId);

    Page<MerchantUserInfoProjection> findPageBrief(Integer pageCount, int pageSize);
}
