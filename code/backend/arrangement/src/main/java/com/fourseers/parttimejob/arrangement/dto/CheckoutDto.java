package com.fourseers.parttimejob.arrangement.dto;

import javax.validation.constraints.NotNull;

public class CheckoutDto {
    @NotNull
    Integer jobId;

    @NotNull
    Float longitude;

    @NotNull
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
