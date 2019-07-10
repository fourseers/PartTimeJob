package com.fourseers.parttimejob.arrangement.dao.impl;

import com.fourseers.parttimejob.arrangement.dao.MerchantUserDao;
import com.fourseers.parttimejob.arrangement.entity.MerchantUser;
import com.fourseers.parttimejob.arrangement.repository.MerchantUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MerchantUserDaoImpl implements MerchantUserDao {

    @Autowired
    private MerchantUserRepository merchantUserRepository;

    public MerchantUser findByUsername(String username) {
        return merchantUserRepository.findByUsername(username);
    }
}
