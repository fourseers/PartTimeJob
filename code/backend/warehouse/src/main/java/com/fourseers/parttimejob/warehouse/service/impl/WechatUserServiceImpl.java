package com.fourseers.parttimejob.warehouse.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.common.entity.Tag;
import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.warehouse.dao.TagDao;
import com.fourseers.parttimejob.warehouse.dao.WechatUserDao;
import com.fourseers.parttimejob.warehouse.dto.WechatUserInfoDto;
import com.fourseers.parttimejob.warehouse.service.WechatUserService;
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
    public boolean updateUserInfo(WechatUser user, JSONObject userInfoDto) {
        if(userInfoDto.getString("name") != null)
            user.setName(userInfoDto.getString("name"));
        if(userInfoDto.getBoolean("gender") != null)
            user.setGender(userInfoDto.getBoolean("gender"));
        if(userInfoDto.getString("country") != null)
            user.setCountry(userInfoDto.getString("country"));
        if(userInfoDto.getString("city") != null)
            user.setCity(userInfoDto.getString("city"));
        if(userInfoDto.getString("identity") != null)
            user.setIdentity(userInfoDto.getString("identity"));
        if(userInfoDto.getString("phone") != null)
            user.setPhone(userInfoDto.getString("phone"));
        if(userInfoDto.getString("education") != null)
            user.setEducation(userInfoDto.getString("education"));
        if(userInfoDto.getJSONArray("tags") != null) {
            JSONArray tags = userInfoDto.getJSONArray("tags");
            Set<Tag> trueTags = new HashSet<>();
            for(int i = 0; i < tags.size(); i++) {
                Tag tag = tagDao.getOne(tags.getInteger(i));
                System.out.println(tag);
                if(tag != null)
                    trueTags.add(tag);
                else
                    return false;
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