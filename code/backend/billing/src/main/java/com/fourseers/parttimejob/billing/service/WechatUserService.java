package com.fourseers.parttimejob.billing.service;

import com.fourseers.parttimejob.common.entity.WechatUser;

public interface WechatUserService {

    WechatUser getUserByOpenid(String openId);
}
