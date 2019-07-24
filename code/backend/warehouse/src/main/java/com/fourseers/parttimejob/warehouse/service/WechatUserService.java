package com.fourseers.parttimejob.warehouse.service;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.warehouse.dto.WechatUserInfoDto;
import org.springframework.stereotype.Service;

@Service
public interface WechatUserService {

    WechatUser findByInternalToken(String internalToken);

    WechatUser getUserByOpenid(String openId);

    WechatUserInfoDto getUserInfo(WechatUser user);

    boolean updateUserInfo(WechatUser user, JSONObject userInfo);

    void save(WechatUser user);
}
