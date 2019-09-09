package com.fourseers.parttimejob.billing.dto;

import javax.validation.constraints.NotNull;

public class YearMonthDto {
    Integer year;
    Integer month;

    @NotNull
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @NotNull
    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }
}
