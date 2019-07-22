package com.fourseers.parttimejob.arrangement.dto;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

public class CheckoutDto {
    @NotNull
    Integer jobId;

    @NotNull
    @Range(min = -180, max = 180)
    Float longitude;

    @NotNull
    @Range(min = -90, max = 90)
    Float latitude;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }
}
