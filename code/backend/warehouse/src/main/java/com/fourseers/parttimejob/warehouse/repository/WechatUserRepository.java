package com.fourseers.parttimejob.warehouse.repository;

import com.fourseers.parttimejob.warehouse.entity.WechatUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WechatUserRepository extends JpaRepository<Integer, WechatUser> {
}
