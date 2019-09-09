package com.fourseers.parttimejob.billing.dao;

import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.stereotype.Repository;

@Repository
public interface WechatUserDao {

    WechatUser findByOpenid(String openid);
}
