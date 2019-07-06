package com.fourseers.parttimejob.auth.service.impl;


import com.fourseers.parttimejob.auth.dao.MerchantUserDao;
import com.fourseers.parttimejob.auth.entity.MerchantUser;
import com.fourseers.parttimejob.auth.service.MerchantUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantUserServiceImpl implements MerchantUserService {

    @Autowired
    private MerchantUserDao merchantUserDao;

    @Override
    public MerchantUser findById(Integer id) {
        return merchantUserDao.findByUid(id);
    }

    @Override
    public MerchantUser findByUsername(String username) {
        return merchantUserDao.findByUsername(username);
    }

    @Override
    public void save(MerchantUser user) {
        merchantUserDao.save(user);
    }
}
