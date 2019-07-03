package com.fourseers.parttimejob.auth.service.impl;

import com.fourseers.parttimejob.auth.dao.WechatUserDao;
import com.fourseers.parttimejob.auth.entity.WechatUser;
import com.fourseers.parttimejob.auth.service.WechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class WechatUserServiceImpl implements WechatUserService {

    @Autowired
    WechatUserDao wechatUserDao;

    public WechatUser findByOpenid(String openid) {
        return wechatUserDao.findByOpenid(openid);
    }

    public void save(WechatUser user) {
        wechatUserDao.save(user);
    }
}
