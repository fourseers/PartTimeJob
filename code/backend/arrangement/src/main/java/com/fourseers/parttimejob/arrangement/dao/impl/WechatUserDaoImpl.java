package com.fourseers.parttimejob.arrangement.dao.impl;

import com.fourseers.parttimejob.arrangement.dao.WechatUserDao;
import com.fourseers.parttimejob.arrangement.repository.WechatUserRepository;
import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class WechatUserDaoImpl implements WechatUserDao {

    @Autowired
    private WechatUserRepository wechatUserRepository;

    @Override
    public WechatUser findByOpenid(String openid) {
        return wechatUserRepository.findByOpenid(openid);
    }
}
