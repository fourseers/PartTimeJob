package com.fourseers.parttimejob.warehouse.service;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.warehouse.dto.WechatUserInfoDto;
import org.springframework.stereotype.Service;

@Service
public interface WechatUserService {

    WechatUser findByOpenid(String openid);

    WechatUser findByInternalToken(String internalToken);

    WechatUserInfoDto getUserInfo(WechatUser user);

    boolean updateUserInfo(WechatUser user, JSONObject userInfo);

    void save(WechatUser user);
}
