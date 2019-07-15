package com.fourseers.parttimejob.warehouse.dao;

import com.fourseers.parttimejob.common.entity.Shop;

import java.util.List;

public interface ShopDao {

    void save(Shop shop);

    Shop findByShopIdAndUserId(int shopId, int userId);

    Shop findByShopIdAndUsername(int shopId, String username);

    List<Shop> findAllByUserId(int userId);

    List<Shop> findAllByUsername(String username);
}
