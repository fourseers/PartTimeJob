package com.fourseers.parttimejob.warehouse.dao.impl;

import com.fourseers.parttimejob.warehouse.dao.WechatUserDao;
import com.fourseers.parttimejob.warehouse.entity.WechatUser;
import com.fourseers.parttimejob.warehouse.repository.WechatUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WechatUserDaoImpl implements WechatUserDao {

    @Autowired
    private WechatUserRepository wechatUserRepository;

    public WechatUser findByOpenid(String openid) {
        return wechatUserRepository.findByOpenid(openid);
    }

    public void save(WechatUser user) {
        wechatUserRepository.save(user);
    }

}
