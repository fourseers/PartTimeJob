package com.fourseers.parttimejob.warehouse.service;

import com.fourseers.parttimejob.warehouse.dto.ShopDto;

import java.util.List;

public interface ShopService {

    void save(ShopDto shopDto, int userId);

    void save(ShopDto shopDto, String username);

    void update(ShopDto shopDto, String username);

    ShopDto findByShopIdAndUserId(int shopId, int userId);

    ShopDto findByShopIdAndUsername(int shopId, String username);

    List<ShopDto> findAllByUserId(int userId);

    List<ShopDto> findAllByUsername(String username);
}
