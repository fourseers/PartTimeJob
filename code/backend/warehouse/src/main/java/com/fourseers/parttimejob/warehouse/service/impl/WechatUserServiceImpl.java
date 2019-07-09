package com.fourseers.parttimejob.warehouse.service.impl;

import com.fourseers.parttimejob.warehouse.dao.WechatUserDao;
import com.fourseers.parttimejob.warehouse.entity.WechatUser;
import com.fourseers.parttimejob.warehouse.service.WechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WechatUserServiceImpl implements WechatUserService {

    @Autowired
    private WechatUserDao wechatUserDao;

    public WechatUser findByOpenid(String openid) {
        return wechatUserDao.findByOpenid(openid);
    }

    public void save(WechatUser user) {
        wechatUserDao.save(user);
    }
}