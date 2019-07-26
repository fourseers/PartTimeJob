package com.fourseers.parttimejob.common.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ScoreKey implements Serializable {

    private int wechatUserId;
    private int shopId;

    public int getWechatUserId() {
        return wechatUserId;
    }

    public void setWechatUserId(int wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
}
