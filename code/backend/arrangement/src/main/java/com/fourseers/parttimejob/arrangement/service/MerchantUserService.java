package com.fourseers.parttimejob.arrangement.service;

import com.fourseers.parttimejob.common.entity.MerchantUser;

public interface MerchantUserService {

    MerchantUser findByUsername(String username);
}
