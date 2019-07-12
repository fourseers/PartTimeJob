package com.fourseers.parttimejob.arrangement.dao;

import com.fourseers.parttimejob.arrangement.entity.MerchantUser;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantUserDao {

    MerchantUser findByUsername(String username);
}
