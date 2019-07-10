package com.fourseers.parttimejob.auth.dao.impl;

import com.fourseers.parttimejob.auth.dao.MerchantUserDao;
import com.fourseers.parttimejob.auth.entity.MerchantUser;
import com.fourseers.parttimejob.auth.repository.MerchantUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MerchantUserImpl implements MerchantUserDao {

    @Autowired
    MerchantUserRepository merchantUserRepository;

    @Override
    public MerchantUser findByUid(Integer uid) {
        Optional<MerchantUser> merchantUser = merchantUserRepository.findById(uid);
        if(merchantUser.isPresent())
            return merchantUser.get();
        else
            return null;
    }

    @Override
    public MerchantUser findByUsername(String username) {
        return merchantUserRepository.findByUsername(username);
    }

    @Override
    public void save(MerchantUser merchantUser) {
        merchantUserRepository.save(merchantUser);
    }
}
