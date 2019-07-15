package com.fourseers.parttimejob.auth.dao;

import com.fourseers.parttimejob.auth.entity.WechatUser;

public interface WechatUserDao {

    WechatUser findByOpenid(String openid);

    void save(WechatUser user);
}
