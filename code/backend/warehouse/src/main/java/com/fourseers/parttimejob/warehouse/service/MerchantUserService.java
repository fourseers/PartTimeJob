package com.fourseers.parttimejob.warehouse.service;

import com.fourseers.parttimejob.common.entity.MerchantUser;
import com.fourseers.parttimejob.warehouse.projection.MerchantUserInfoProjection;
import org.springframework.data.domain.Page;

public interface MerchantUserService {

    void save(MerchantUser merchantUser);

    MerchantUser findByUsername(String username);

    MerchantUserInfoProjection findBriefByUserId(Integer userId);

    Page<MerchantUserInfoProjection> findPageBrief(Integer pageCount, int pageSize);

    void ban(Integer userId, boolean banned);
}
