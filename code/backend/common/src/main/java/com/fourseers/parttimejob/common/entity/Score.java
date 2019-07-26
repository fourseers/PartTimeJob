package com.fourseers.parttimejob.common.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/*
 * User's score to shops
 */
@Entity
@Table(name = "wechat_user_shop_score")
public class Score {

    @EmbeddedId
    private ScoreKey scoreId;

    @ManyToOne
    @MapsId("wechatUserId")
    private WechatUser wechatUser;

    @ManyToOne
    @MapsId("shopId")
    private Shop shop;

    @NotNull
    @Min(0)
    @Max(5)
    private Integer score;

    public ScoreKey getScoreId() {
        return scoreId;
    }

    public void setScoreId(ScoreKey scoreId) {
        this.scoreId = scoreId;
    }

    public WechatUser getWechatUser() {
        return wechatUser;
    }

    public void setWechatUser(WechatUser wechatUser) {
        this.wechatUser = wechatUser;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
