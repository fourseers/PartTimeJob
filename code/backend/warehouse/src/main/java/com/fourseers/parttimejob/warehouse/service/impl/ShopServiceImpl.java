package com.fourseers.parttimejob.warehouse.service.impl;

import com.fourseers.parttimejob.common.entity.MerchantUser;
import com.fourseers.parttimejob.common.entity.Shop;
import com.fourseers.parttimejob.warehouse.dao.MerchantUserDao;
import com.fourseers.parttimejob.warehouse.dao.ShopDao;
import com.fourseers.parttimejob.warehouse.dto.ShopDto;
import com.fourseers.parttimejob.warehouse.service.ShopService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    public void update(ShopDto shopDto, String username) {

        MerchantUser merchantUser = merchantUserDao.findByUsername(username);
        Shop previousShop = shopDao.findByShopIdAndUsername(shopDto.getShopId(), username);

        if (previousShop == null) {
            throw new RuntimeException("shop not exist or not belong to");
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

    public Page<ShopDto> findPageByUserId(int userId, int pageCount, int pageSize) {
        Page<Shop> shops = shopDao.findPageByUserId(userId, pageCount, pageSize);

        return shops.map(shop -> modelMapper.map(shop, ShopDto.class));
    }

    public Page<ShopDto> findPageByUsername(String username, int pageCount, int pageSize) {
        Page<Shop> shops = shopDao.findPageByUsername(username, pageCount, pageSize);

        return shops.map(shop -> modelMapper.map(shop, ShopDto.class));
    }
}
