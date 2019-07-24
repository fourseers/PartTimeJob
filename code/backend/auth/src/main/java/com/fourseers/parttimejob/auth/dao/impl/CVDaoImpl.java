package com.fourseers.parttimejob.auth.dao.impl;

import com.fourseers.parttimejob.auth.dao.CVDao;
import com.fourseers.parttimejob.auth.repository.CVRepository;
import com.fourseers.parttimejob.common.entity.CV;
import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CVDaoImpl implements CVDao {

    @Autowired
    CVRepository cvRepository;

    @Override
    public CV saveDefault(WechatUser wechatUser) {
        CV cv = new CV();
        cv.setUserId(wechatUser.getUserId());
        cv.setName(wechatUser.getName());
        cv.setIdentity(wechatUser.getIdentity());
        cv.setPhone(wechatUser.getPhone());
        cv.setGender(wechatUser.getGender());
        cv.setEducation(wechatUser.getEducation());
        cvRepository.save(cv);
        return cv;
    }
}
