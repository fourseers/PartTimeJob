package com.fourseers.parttimejob.warehouse.dao;

import com.fourseers.parttimejob.warehouse.entity.WechatUser;

public interface WechatUserDao {

    WechatUser findByOpenid(String openid);

    void save(WechatUser user);
}

