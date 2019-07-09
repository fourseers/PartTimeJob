package com.fourseers.parttimejob.warehouse.service;

import com.fourseers.parttimejob.warehouse.entity.WechatUser;
import org.springframework.stereotype.Service;

@Service
public interface WechatUserService {

    WechatUser findByOpenid(String openid);

    void save(WechatUser user);
}
