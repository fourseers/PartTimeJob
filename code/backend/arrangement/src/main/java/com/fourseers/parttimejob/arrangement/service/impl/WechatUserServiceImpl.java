package com.fourseers.parttimejob.arrangement.service.impl;

import com.fourseers.parttimejob.arrangement.dao.WechatUserDao;
import com.fourseers.parttimejob.arrangement.service.WechatUserService;
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
