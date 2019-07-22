package com.fourseers.parttimejob.auth.dao;

import com.fourseers.parttimejob.common.entity.CV;
import com.fourseers.parttimejob.common.entity.WechatUser;

public interface CVDao {
    CV saveDefault(WechatUser wechatUser);
}
