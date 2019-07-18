package com.fourseers.parttimejob.warehouse.dao.impl;

import com.fourseers.parttimejob.common.entity.Shop;
import com.fourseers.parttimejob.warehouse.dao.ShopDao;
import com.fourseers.parttimejob.warehouse.projection.ShopBriefProjection;
import com.fourseers.parttimejob.warehouse.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShopDaoImpl implements ShopDao {

    @Autowired
    private ShopRepository shopRepository;

    public void save(Shop shop) {
        shopRepository.save(shop);
    }

    public Shop findByShopIdAndUserId(int shopId, int userId) {
        return shopRepository.findByShopIdAndUserId(shopId, userId);
    }

    public Shop findByShopIdAndUsername(int shopId, String username) {
        return shopRepository.findByShopIdAndUsername(shopId, username);
    }

    public Page<Shop> findPageByUserId(int userId, int pageCount, int pageSize) {
        Pageable pageable = PageRequest.of(pageCount, pageSize);
        return shopRepository.findPageByUserId(userId, pageable);
    }

    public Page<Shop> findPageByUsername(String username, int pageCount, int pageSize) {
        Pageable pageable = PageRequest.of(pageCount, pageSize);
        return shopRepository.findPageByUsername(username, pageable);
    }

    public List<ShopBriefProjection> findShopBriefByUsername(String username) {
        return shopRepository.findShopBriefByUsername(username);
    }
}
