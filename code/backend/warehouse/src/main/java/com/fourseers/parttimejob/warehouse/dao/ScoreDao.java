package com.fourseers.parttimejob.warehouse.dao;

import com.fourseers.parttimejob.common.entity.WechatUser;

public interface ScoreDao {
    boolean submitOne(WechatUser user, int shopId, int score);

    Integer getOne(WechatUser user, int shopId);
}
