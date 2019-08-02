package com.fourseers.parttimejob.billing.repository;

import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WechatUserRepository extends JpaRepository<WechatUser, Integer> {
    public WechatUser findByOpenid(String openid);
}
