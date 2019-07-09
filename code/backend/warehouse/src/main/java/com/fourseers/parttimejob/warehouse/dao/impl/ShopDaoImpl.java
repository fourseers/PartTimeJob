package com.fourseers.parttimejob.warehouse.dao.impl;

import com.fourseers.parttimejob.warehouse.dao.ShopDao;
import com.fourseers.parttimejob.warehouse.entity.Shop;
import com.fourseers.parttimejob.warehouse.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Shop> findAllByUserId(int userId) {
        return shopRepository.findAllByUserId(userId);
    }

    public List<Shop> findAllByUsername(String username) {
        return shopRepository.findAllByUsername(username);
    }
}
