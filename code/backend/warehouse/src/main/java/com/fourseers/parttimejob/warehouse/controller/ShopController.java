package com.fourseers.parttimejob.warehouse.controller;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.common.util.Response;
import com.fourseers.parttimejob.common.util.ResponseBuilder;
import com.fourseers.parttimejob.warehouse.dto.ShopDto;
import com.fourseers.parttimejob.warehouse.projection.ShopBriefProjection;
import com.fourseers.parttimejob.warehouse.service.ShopService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/merchant")
public class ShopController {

    @Autowired
    private ShopService shopService;

    private final static int PAGE_SIZE = 10;

    @RequestMapping(value = "/shop", method = RequestMethod.POST)
    public ResponseEntity<Response<JSONObject>> createShop(@RequestBody ShopDto shop,
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

    @ApiOperation(value = "Get one shop info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "shop not exist or not belong to"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-access-token", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "/shop", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Response<ShopDto>> getShop(
            @ApiParam(value = "This param tells the server which shop_id to query.")
            @RequestParam(value = "shop_id") Integer shopId,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String username) {

        ShopDto shop = shopService.findByShopIdAndUsername(shopId, username);

        if (shop == null) {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "shop not exist or not belong to");
        } else {
            return ResponseBuilder.build(HttpStatus.OK, shop, "success");
        }
    }

    @ApiOperation(value = "Get one page of shop info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "incorrect param / no shops"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-access-token", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "/shops", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Response<Page<ShopDto>>> getShops(
            @ApiParam(value = "This param tells the server which page to query, starting from 0, with each page having 10 items.")
            @RequestParam(value = "page_count") Integer pageCount,
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String username) {

        Page<ShopDto> shops;
        try {
            shops = shopService.findPageByUsername(username, pageCount, PAGE_SIZE);
        } catch (RuntimeException ex) {
            if (ex.getMessage().contains("Page index must not be less than zero!")) {
                return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "incorrect param");
            } else {
                return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, ex.getMessage());
            }
        }

        if (shops.isEmpty()) {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "no shops");
        } else {
            return ResponseBuilder.build(HttpStatus.OK, shops, "success");
        }
    }

    @ApiOperation(value = "Get brief info about all shops")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "no shops"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-access-token", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "/shops/brief", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Response<List<ShopBriefProjection>>> getShopsBrief(
            @ApiParam(hidden = true) @RequestHeader("x-internal-token") String username) {

        List<ShopBriefProjection> shopBriefProjectionList = shopService.findShopBriefByUsername(username);
        if (shopBriefProjectionList.isEmpty()) {
            return ResponseBuilder.build(HttpStatus.BAD_REQUEST, null, "no shops");
        } else {
            return ResponseBuilder.build(HttpStatus.OK, shopBriefProjectionList, "success");
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
    @RequestMapping(value = "/shop", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Response<Object>> updateShop(@RequestBody ShopDto shop,
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
