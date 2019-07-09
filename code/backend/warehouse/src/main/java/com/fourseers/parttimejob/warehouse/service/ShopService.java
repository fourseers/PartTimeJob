package com.fourseers.parttimejob.warehouse.service;

import com.fourseers.parttimejob.warehouse.dto.ShopDto;

import java.util.List;

public interface ShopService {

    void save(ShopDto shopDto, int userId);

    ShopDto findByShopIdAndUserId(int shopId, int userId);

    List<ShopDto> findAllByUserId(int userId);
}
