package com.fourseers.parttimejob.warehouse.dao.impl;

import com.fourseers.parttimejob.warehouse.dao.MerchantUserDao;
import com.fourseers.parttimejob.warehouse.entity.MerchantUser;
import com.fourseers.parttimejob.warehouse.repository.MerchantUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MerchantUserDaoImpl implements MerchantUserDao {

    @Autowired
    MerchantUserRepository merchantUserRepository;

    public void save(MerchantUser merchantUser) {
        merchantUserRepository.save(merchantUser);
    }

    public MerchantUser findByUserId(Integer userId) {
        return merchantUserRepository.findByUserId(userId);
    }
}
