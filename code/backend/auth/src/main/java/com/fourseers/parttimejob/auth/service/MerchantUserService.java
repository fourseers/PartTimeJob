package com.fourseers.parttimejob.auth.service;

import com.fourseers.parttimejob.auth.entity.MerchantUser;
import org.springframework.stereotype.Service;

@Service
public interface MerchantUserService {

    MerchantUser findById(Integer id);
    MerchantUser findByUsername(String username);

    void save(MerchantUser user);
}
