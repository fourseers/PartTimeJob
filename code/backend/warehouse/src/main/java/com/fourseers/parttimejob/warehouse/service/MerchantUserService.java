package com.fourseers.parttimejob.warehouse.service;

import com.fourseers.parttimejob.common.entity.MerchantUser;
import com.fourseers.parttimejob.warehouse.projection.MerchantUserInfoProjection;

public interface MerchantUserService {

    void save(MerchantUser merchantUser);

    MerchantUser findByUsername(String username);

    MerchantUserInfoProjection findBriefByUserId(Integer userId);
}
