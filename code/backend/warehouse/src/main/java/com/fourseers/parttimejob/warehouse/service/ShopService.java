package com.fourseers.parttimejob.warehouse.service;

import com.fourseers.parttimejob.warehouse.dto.ShopDto;
import com.fourseers.parttimejob.warehouse.projection.ShopBriefProjection;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ShopService {

    void save(ShopDto shopDto, int userId);

    void save(ShopDto shopDto, String username);

    void update(ShopDto shopDto, String username);

    ShopDto findByShopIdAndUserId(int shopId, int userId);

    ShopDto findByShopIdAndUsername(int shopId, String username);

    Page<ShopDto> findPageByUserId(int userId, int pageCount, int pageSize);

    Page<ShopDto> findPageByUsername(String username, int pageCount, int pageSize);

    List<ShopBriefProjection> findShopBriefByUsername(String username);
}
