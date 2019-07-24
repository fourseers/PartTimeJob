package com.fourseers.parttimejob.arrangement.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class WorkRemarkDto {
    private Integer workId;
    private Integer score;

    @NotNull
    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    @Min(value = 0)
    @Max(value = 5)
    @NotNull
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
