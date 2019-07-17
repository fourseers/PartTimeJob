package com.fourseers.parttimejob.auth.dao.impl;

import com.fourseers.parttimejob.auth.dao.WechatUserDao;
import com.fourseers.parttimejob.auth.repository.WechatUserRepository;
import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
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
