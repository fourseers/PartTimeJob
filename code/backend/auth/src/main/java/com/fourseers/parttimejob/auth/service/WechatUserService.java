package com.fourseers.parttimejob.auth.service;

import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.stereotype.Service;

@Service
public interface WechatUserService {

    WechatUser findByOpenid(String openid);

    void save(WechatUser user);
}
