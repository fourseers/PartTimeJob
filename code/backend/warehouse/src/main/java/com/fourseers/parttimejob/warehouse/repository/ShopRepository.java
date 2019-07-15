package com.fourseers.parttimejob.warehouse.repository;

import com.fourseers.parttimejob.common.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Integer> {

    @Query("select shop " +
           "from Shop shop inner join MerchantUser merchantUser " +
           "on shop.shopId = ?1 and merchantUser.userId = ?2 and shop.company = merchantUser.company")
    Shop findByShopIdAndUserId(int shopId, int userId);

    @Query("select shop " +
           "from Shop shop inner join MerchantUser merchantUser " +
           "on shop.shopId = ?1 and merchantUser.username = ?2 and shop.company = merchantUser.company")
    Shop findByShopIdAndUsername(int shopId, String username);

    @Query("select shop " +
           "from Shop shop inner join MerchantUser merchantUser " +
           "on merchantUser.userId = ?1 and shop.company = merchantUser.company")
    List<Shop> findAllByUserId(int userId);

    @Query("select shop " +
           "from Shop shop inner join MerchantUser merchantUser " +
           "on merchantUser.username = ?1 and shop.company = merchantUser.company")
    List<Shop> findAllByUsername(String username);
}
