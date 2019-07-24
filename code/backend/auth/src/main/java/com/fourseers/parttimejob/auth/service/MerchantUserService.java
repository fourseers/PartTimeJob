package com.fourseers.parttimejob.auth.service;

import com.fourseers.parttimejob.common.entity.MerchantUser;
import org.springframework.stereotype.Service;

@Service
public interface MerchantUserService {

    MerchantUser findById(Integer id);
    MerchantUser findByUsername(String username);

    boolean register(MerchantUser user);
    void save(MerchantUser user);
}
