package com.fourseers.parttimejob.warehouse.controller;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.warehouse.dto.ShopDto;
import com.fourseers.parttimejob.warehouse.service.ShopService;
import com.fourseers.parttimejob.warehouse.util.ResponseBuilder;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/merchant/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> createShop(@RequestBody ShopDto shop,
                                                 @RequestHeader("x-internal-token") String username) {

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
            shopService.save(shop, username);
        } catch (DataIntegrityViolationException ex) {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "shop name exists");
        } catch (RuntimeException ex) {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, ex.getMessage());
        }
        return ResponseBuilder.build(HttpStatus.OK, null, "success");
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> getShop(@RequestParam(value = "shop_id", required = false) Integer shopId,
                                              @RequestHeader("x-internal-token") String username) {

        if (shopId != null) {
            ShopDto shop = shopService.findByShopIdAndUsername(shopId, username);

            if (shop == null) {
                return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "shop not exist or not belong to");
            } else {
                JSONObject body = new JSONObject();
                body.put("shop", shop);
                return ResponseBuilder.build(HttpStatus.OK, body, "success");
            }
        } else {
            List<ShopDto> shops = shopService.findAllByUsername(username);

            if (shops.size() == 0) {
                return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "no shops");
            } else {
                JSONObject body = new JSONObject();
                body.put("shops", shops);
                return ResponseBuilder.build(HttpStatus.OK, body, "success");
            }
        }
    }

    @ApiOperation(value = "Update shop info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "incorrect param / shop not exist or not belong to / shop name exists"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-access-token", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<JSONObject> updateShop(@RequestBody ShopDto shop,
                                                 @ApiParam(hidden=true) @RequestHeader("x-internal-token") String username) {

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
            shopService.update(shop, username);
        } catch (DataIntegrityViolationException ex) {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "shop name exists");
        } catch (JpaObjectRetrievalFailureException ex) {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "incorrect param");
        } catch (RuntimeException ex) {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, ex.getMessage());
        }
        return ResponseBuilder.build(HttpStatus.OK, null, "success");
    }
}
