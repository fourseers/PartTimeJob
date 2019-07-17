package com.fourseers.parttimejob.warehouse.dao;

import com.fourseers.parttimejob.common.entity.MerchantUser;
import com.fourseers.parttimejob.warehouse.projection.MerchantUserInfoProjection;

public interface MerchantUserDao {

    void save(MerchantUser merchantUser);

    MerchantUser findByUserId(Integer userId);

    MerchantUser findByUsername(String username);

    MerchantUserInfoProjection findBriefByUserId(Integer userId);
}
