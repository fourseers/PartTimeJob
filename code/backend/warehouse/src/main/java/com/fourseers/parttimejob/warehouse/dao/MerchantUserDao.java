package com.fourseers.parttimejob.warehouse.dao;

import com.fourseers.parttimejob.warehouse.entity.MerchantUser;

public interface MerchantUserDao {

    void save(MerchantUser merchantUser);

    MerchantUser findByUserId(Integer userId);

    MerchantUser findByUsername(String username);
}
