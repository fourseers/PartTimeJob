package com.fourseers.parttimejob.warehouse.dao.impl;

import com.fourseers.parttimejob.common.entity.MerchantUser;
import com.fourseers.parttimejob.warehouse.dao.MerchantUserDao;
import com.fourseers.parttimejob.warehouse.projection.MerchantUserInfoProjection;
import com.fourseers.parttimejob.warehouse.repository.MerchantUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class MerchantUserDaoImpl implements MerchantUserDao {

    @Autowired
    MerchantUserRepository merchantUserRepository;

    public void save(MerchantUser merchantUser) {
        merchantUserRepository.save(merchantUser);
    }

    public MerchantUser findByUserId(Integer userId) {
        return merchantUserRepository.findByUserId(userId);
    }

    public MerchantUser findByUsername(String username) {
        return merchantUserRepository.findByUsername(username);
    }

    public MerchantUserInfoProjection findBriefByUserId(Integer userId) {
        return merchantUserRepository.findBriefByUserId(userId);
    }

    public Page<MerchantUserInfoProjection> findPageBrief(Integer pageCount, int pageSize) {
        Pageable pageable = PageRequest.of(pageCount, pageSize);
        return merchantUserRepository.findAllOrderByUserId(pageable);
    }
}
