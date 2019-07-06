package com.fourseers.parttimejob.auth.repository;

import com.fourseers.parttimejob.auth.entity.WechatUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WechatUserRepository extends JpaRepository<WechatUser, Integer> {

    WechatUser findByOpenid(String openid);
}
