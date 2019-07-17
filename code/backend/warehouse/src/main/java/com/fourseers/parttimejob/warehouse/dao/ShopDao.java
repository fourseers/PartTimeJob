package com.fourseers.parttimejob.warehouse.dao;

import com.fourseers.parttimejob.common.entity.Shop;
import com.fourseers.parttimejob.warehouse.projection.ShopBriefProjection;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ShopDao {

    void save(Shop shop);

    Shop findByShopIdAndUserId(int shopId, int userId);

    Shop findByShopIdAndUsername(int shopId, String username);

    Page<Shop> findPageByUserId(int userId, int pageCount, int pageSize);

    Page<Shop> findPageByUsername(String username, int pageCount, int pageSize);

    List<ShopBriefProjection> findShopBriefByUsername(String username);
}
