package com.fourseers.parttimejob.arrangement.dao;

import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.stereotype.Repository;

@Repository
public interface WechatUserDao {

    WechatUser findByOpenid(String openid);
}
