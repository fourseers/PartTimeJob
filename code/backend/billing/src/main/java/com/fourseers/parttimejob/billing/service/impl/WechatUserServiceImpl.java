package com.fourseers.parttimejob.billing.service.impl;

import com.fourseers.parttimejob.billing.dao.WechatUserDao;
import com.fourseers.parttimejob.billing.service.WechatUserService;
import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WechatUserServiceImpl implements WechatUserService {

    @Autowired
    private WechatUserDao wechatUserDao;

    @Override
    public WechatUser getUserByOpenid(String openId) {
        return wechatUserDao.findByOpenid(openId);
    }
}
