package com.fourseers.parttimejob.warehouse.service;

import com.fourseers.parttimejob.warehouse.dto.WechatUserInfoDto;
import com.fourseers.parttimejob.warehouse.entity.WechatUser;
import org.springframework.stereotype.Service;

@Service
public interface WechatUserService {

    WechatUser findByOpenid(String openid);

    WechatUser findByInternalToken(String internalToken);

    WechatUserInfoDto getUserInfo(WechatUser user);

    boolean updateUserInfo(WechatUser user, WechatUserInfoDto userInfoDto);

    void save(WechatUser user);
}
