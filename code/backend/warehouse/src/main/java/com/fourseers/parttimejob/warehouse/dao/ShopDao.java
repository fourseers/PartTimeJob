package com.fourseers.parttimejob.warehouse.dao;

import com.fourseers.parttimejob.warehouse.entity.Shop;

import java.util.List;

public interface ShopDao {

    void save(Shop shop);

    Shop findByShopIdAndUserId(int shopId, int userId);

    List<Shop> findAllByUserId(int userId);
}
