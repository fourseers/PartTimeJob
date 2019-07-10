package com.fourseers.parttimejob.warehouse.service.impl;

import com.fourseers.parttimejob.warehouse.dao.TagDao;
import com.fourseers.parttimejob.warehouse.dao.WechatUserDao;
import com.fourseers.parttimejob.warehouse.dto.WechatUserInfoDto;
import com.fourseers.parttimejob.warehouse.entity.Tag;
import com.fourseers.parttimejob.warehouse.entity.WechatUser;
import com.fourseers.parttimejob.warehouse.service.WechatUserService;
import com.netflix.discovery.converters.Auto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class WechatUserServiceImpl implements WechatUserService {

    @Autowired
    private WechatUserDao wechatUserDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${app.wechat_user_prefix}")
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
        if(userInfoDto.getGender() != null)
            user.setGender(userInfoDto.getGender());
        if(userInfoDto.getCity() != null)
            user.setCity(userInfoDto.getCity());
        if(userInfoDto.getCountry() != null)
            user.setCountry(userInfoDto.getCountry());
        if(userInfoDto.getIdentity() != null)
            user.setIdentity(userInfoDto.getIdentity());
        if(userInfoDto.getPhone() != null)
            user.setPhone(userInfoDto.getPhone());
        if(userInfoDto.getEducation() != null)
            user.setEducation(userInfoDto.getEducation());
        if(userInfoDto.getTags() != null) {
            Set<Tag> tags = userInfoDto.getTags();
            Set<Tag> trueTags = new HashSet<>();
            for (Tag tag: tags) {
                Tag trueTag = tagDao.getOne(tag.getId());
                if(trueTag != null) trueTags.add(trueTag);
            }
            user.setTags(trueTags);
        }
        wechatUserDao.save(user);
        return true;
    }

    public void save(WechatUser user) {
        wechatUserDao.save(user);
    }
}