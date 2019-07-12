package com.fourseers.parttimejob.arrangement.service;

import com.fourseers.parttimejob.arrangement.entity.MerchantUser;

public interface MerchantUserService {

    MerchantUser findByUsername(String username);
}
