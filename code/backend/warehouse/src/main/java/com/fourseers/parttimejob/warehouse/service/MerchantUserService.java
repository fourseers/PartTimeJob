package com.fourseers.parttimejob.warehouse.service;

import com.fourseers.parttimejob.common.entity.MerchantUser;

public interface MerchantUserService {

    void save(MerchantUser merchantUser);

    MerchantUser findByUsername(String username);
}
