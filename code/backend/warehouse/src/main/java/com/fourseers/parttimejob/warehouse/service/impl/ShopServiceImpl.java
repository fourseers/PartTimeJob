package com.fourseers.parttimejob.warehouse.service.impl;

import com.fourseers.parttimejob.warehouse.dao.MerchantUserDao;
import com.fourseers.parttimejob.warehouse.dao.ShopDao;
import com.fourseers.parttimejob.warehouse.dto.ShopDto;
import com.fourseers.parttimejob.warehouse.entity.MerchantUser;
import com.fourseers.parttimejob.warehouse.entity.Shop;
import com.fourseers.parttimejob.warehouse.service.ShopService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private MerchantUserDao merchantUserDao;

    @Autowired
    private ModelMapper modelMapper;

    public void save(ShopDto shopDto, int userId) {

        MerchantUser merchantUser = merchantUserDao.findByUserId(userId);
        if (merchantUser.getCompany() == null) {
            throw new RuntimeException("user not belong to any company");
        }

        Shop shop = modelMapper.map(shopDto, Shop.class);
        shop.setCompany(merchantUser.getCompany());
        shopDao.save(shop);
    }

    public void save(ShopDto shopDto, String username) {

        MerchantUser merchantUser = merchantUserDao.findByUsername(username);
        if (merchantUser.getCompany() == null) {
            throw new RuntimeException("user not belong to any company");
        }

        Shop shop = modelMapper.map(shopDto, Shop.class);
        shop.setCompany(merchantUser.getCompany());
        shopDao.save(shop);
    }

    public ShopDto findByShopIdAndUserId(int shopId, int userId) {
        Shop shop = shopDao.findByShopIdAndUserId(shopId, userId);

        if (shop == null) {
            return null;
        }

        return modelMapper.map(shop, ShopDto.class);
    }

    public ShopDto findByShopIdAndUsername(int shopId, String username) {
        Shop shop = shopDao.findByShopIdAndUsername(shopId, username);

        if (shop == null) {
            return null;
        }

        return modelMapper.map(shop, ShopDto.class);
    }

    public List<ShopDto> findAllByUserId(int userId) {
        List<Shop> shops = shopDao.findAllByUserId(userId);
        if (shops == null) {
            return null;
        }

        List<ShopDto> result = new ArrayList<>();
        for (Shop shop : shops) {
            result.add(modelMapper.map(shop, ShopDto.class));
        }

        return result;
    }

    public List<ShopDto> findAllByUsername(String username) {
        List<Shop> shops = shopDao.findAllByUsername(username);
        if (shops == null) {
            return null;
        }

        List<ShopDto> result = new ArrayList<>();
        for (Shop shop : shops) {
            result.add(modelMapper.map(shop, ShopDto.class));
        }

        return result;
    }
}
