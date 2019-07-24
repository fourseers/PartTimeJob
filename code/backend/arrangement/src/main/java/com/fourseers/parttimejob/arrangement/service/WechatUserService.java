package com.fourseers.parttimejob.arrangement.service;

import com.fourseers.parttimejob.common.entity.WechatUser;

public interface WechatUserService {

    WechatUser getUserByOpenid(String openId);
}
