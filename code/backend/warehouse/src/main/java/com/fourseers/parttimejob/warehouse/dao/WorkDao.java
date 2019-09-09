package com.fourseers.parttimejob.warehouse.dao;

import com.fourseers.parttimejob.common.entity.WechatUser;

public interface WorkDao {
    boolean haveWorkedIn(WechatUser user, Integer shopId);
}
