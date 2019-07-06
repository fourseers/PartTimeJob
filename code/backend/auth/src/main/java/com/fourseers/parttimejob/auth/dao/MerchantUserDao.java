package com.fourseers.parttimejob.auth.dao;

import com.fourseers.parttimejob.auth.entity.MerchantUser;

public interface MerchantUserDao {

    MerchantUser findByUid(Integer uid);
    MerchantUser findByUsername(String username);

    void save(MerchantUser merchantUser);
}
