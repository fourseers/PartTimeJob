package com.fourseers.parttimejob.warehouse.service.impl;

import com.fourseers.parttimejob.warehouse.dao.WechatUserDao;
import com.fourseers.parttimejob.warehouse.dto.WechatUserInfoDto;
import com.fourseers.parttimejob.warehouse.entity.WechatUser;
import com.fourseers.parttimejob.warehouse.service.WechatUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WechatUserServiceImpl implements WechatUserService {

    @Autowired
    private WechatUserDao wechatUserDao;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${app.wechat_user_prefix")
    private String WECHAT_USER_PREFIX;

    public WechatUser findByOpenid(String openid) {
        return wechatUserDao.findByOpenid(openid);
    }

    @Override
    public WechatUser findByInternalToken(String internalToken) {
        if(!internalToken.startsWith(WECHAT_USER_PREFIX))
            return null;
        return wechatUserDao.findByOpenid(internalToken.substring(WECHAT_USER_PREFIX.length()));
    }

    @Override
    public WechatUserInfoDto getUserInfo(WechatUser user) {
        return modelMapper.map(user, WechatUserInfoDto.class);
    }

    @Override
    public boolean updateUserInfo(WechatUser user, WechatUserInfoDto userInfoDto) {

    }

    public void save(WechatUser user) {
        wechatUserDao.save(user);
    }
}