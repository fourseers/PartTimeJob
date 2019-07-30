package com.fourseers.parttimejob.arrangement.dto;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CheckinDto {

    @NotNull
    Integer jobId;

    @DecimalMin("-180")
    @DecimalMax("180")
    BigDecimal longitude;

    @DecimalMin("-90")
    @DecimalMax("90")
    BigDecimal latitude;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
}
