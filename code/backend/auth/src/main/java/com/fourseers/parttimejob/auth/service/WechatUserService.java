package com.fourseers.parttimejob.auth.service;

import com.fourseers.parttimejob.auth.entity.WechatUser;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public interface WechatUserService {

    WechatUser findByOpenid(String openid);

    void save(WechatUser user);
}
