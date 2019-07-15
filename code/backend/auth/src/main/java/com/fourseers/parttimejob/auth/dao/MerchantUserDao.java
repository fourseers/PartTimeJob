package com.fourseers.parttimejob.auth.dao;

import com.fourseers.parttimejob.common.entity.MerchantUser;

public interface MerchantUserDao {

    MerchantUser findByUid(Integer uid);
    MerchantUser findByUsername(String username);

    void save(MerchantUser merchantUser);
}
