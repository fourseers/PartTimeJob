package com.fourseers.parttimejob.warehouse.service.impl;

import com.fourseers.parttimejob.common.entity.MerchantUser;
import com.fourseers.parttimejob.warehouse.dao.MerchantUserDao;
import com.fourseers.parttimejob.warehouse.projection.MerchantUserInfoProjection;
import com.fourseers.parttimejob.warehouse.service.MerchantUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    public MerchantUser findByUsername(String username) {
        return merchantUserDao.findByUsername(username);
    }

    public MerchantUserInfoProjection findBriefByUserId(Integer userId) {
        return merchantUserDao.findBriefByUserId(userId);
    }

    public Page<MerchantUserInfoProjection> findPageBrief(Integer pageCount, int pageSize) {
        return merchantUserDao.findPageBrief(pageCount, pageSize);
    }

    public void ban(Integer userId, boolean banned) {
        MerchantUser user = merchantUserDao.findByUserId(userId);

        if (user == null) {
            throw new RuntimeException("user not exist");
        }
        user.setBanned(banned);
        merchantUserDao.save(user);
    }
}
