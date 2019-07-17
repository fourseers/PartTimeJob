package com.fourseers.parttimejob.warehouse.service;

import com.fourseers.parttimejob.warehouse.dto.ShopDto;
import org.springframework.data.domain.Page;

public interface ShopService {

    void save(ShopDto shopDto, int userId);

    void save(ShopDto shopDto, String username);

    void update(ShopDto shopDto, String username);

    ShopDto findByShopIdAndUserId(int shopId, int userId);

    ShopDto findByShopIdAndUsername(int shopId, String username);

    Page<ShopDto> findPageByUserId(int userId, int pageCount, int pageSize);

    Page<ShopDto> findPageByUsername(String username, int pageCount, int pageSize);
}
