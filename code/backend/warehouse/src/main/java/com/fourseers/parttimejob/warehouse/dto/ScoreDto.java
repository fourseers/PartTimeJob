package com.fourseers.parttimejob.warehouse.dto;

import javax.validation.constraints.NotNull;

public class ScoreDto {
    @NotNull
    private Integer shopId;

    @NotNull
    private Integer score;

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
