package com.fourseers.parttimejob.warehouse.controller;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.warehouse.dto.ShopDto;
import com.fourseers.parttimejob.warehouse.service.ShopService;
import com.fourseers.parttimejob.warehouse.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/merchant/shop/")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> createShop(@RequestBody ShopDto shop,
                                                 @RequestHeader("x-internal-token") Integer userId) {

        if (shop.getAddress() == null ||
            shop.getBrand() == null ||
            shop.getCity() == null ||
            shop.getIndustry() == null ||
            shop.getIntroduction() == null ||
            shop.getLatitude() == null ||
            shop.getLongitude() == null ||
            shop.getProvince() == null ||
            shop.getShopName() == null) {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "incorrect param");
        }
        try {
            shopService.save(shop, userId);
        } catch (DataIntegrityViolationException ex) {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "shop name exists");
        } catch (RuntimeException ex) {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, ex.getMessage());
        }
        return ResponseBuilder.build(HttpStatus.OK, null, "success");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> getShop(@RequestParam(value = "shop_id", required = false) Integer shopId,
                                              @RequestHeader("x-internal-token") Integer userId) {

        if (shopId != null) {
            ShopDto shop = shopService.findByShopIdAndUserId(shopId, userId);

            if (shop == null) {
                return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "shop not exist or not belong to");
            } else {
                JSONObject body = new JSONObject();
                body.put("shop", shop);
                return ResponseBuilder.build(HttpStatus.OK, body, "success");
            }
        } else {
            List<ShopDto> shops = shopService.findAllByUserId(userId);

            if (shops.size() == 0) {
                return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "no shops");
            } else {
                JSONObject body = new JSONObject();
                body.put("shops", shops);
                return ResponseBuilder.build(HttpStatus.OK, body, "success");
            }
        }
    }
}
