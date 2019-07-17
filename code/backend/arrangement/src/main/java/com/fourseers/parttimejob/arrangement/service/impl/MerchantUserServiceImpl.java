package com.fourseers.parttimejob.arrangement.service.impl;

import com.fourseers.parttimejob.arrangement.dao.MerchantUserDao;
import com.fourseers.parttimejob.arrangement.service.MerchantUserService;
import com.fourseers.parttimejob.common.entity.MerchantUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MerchantUserServiceImpl implements MerchantUserService {

    @Autowired
    private MerchantUserDao merchantUserDao;

    public MerchantUser findByUsername(String username) {
        return merchantUserDao.findByUsername(username);
    }
}
