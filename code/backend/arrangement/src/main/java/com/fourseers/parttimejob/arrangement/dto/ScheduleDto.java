package com.fourseers.parttimejob.arrangement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.sql.Time;

public class ScheduleDto {
    // job info
    private Integer jobId;
    private Integer shopId;
    private String jobName;
    private String shopName;
    private Date beginDate;
    private Date endDate;
    private Time beginTime;
    private Time endTime;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "GMT+8")
    public Time getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Time beginTime) {
        this.beginTime = beginTime;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "GMT+8")
    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}
