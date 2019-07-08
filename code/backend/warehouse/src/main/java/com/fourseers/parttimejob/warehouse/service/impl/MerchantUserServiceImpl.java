package com.fourseers.parttimejob.warehouse.service.impl;

import com.fourseers.parttimejob.warehouse.dao.MerchantUserDao;
import com.fourseers.parttimejob.warehouse.entity.MerchantUser;
import com.fourseers.parttimejob.warehouse.service.MerchantUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MerchantUserServiceImpl implements MerchantUserService {

    @Autowired
    MerchantUserDao merchantUserDao;

    public void save(MerchantUser merchantUser) {
        merchantUserDao.save(merchantUser);
    }
}
