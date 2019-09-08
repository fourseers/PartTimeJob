package com.fourseers.parttimejob.arrangement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fourseers.parttimejob.common.entity.CV;

import java.sql.Date;
import java.sql.Timestamp;

public class ApplyOutDto {

    private Integer applicationId;

    private Timestamp createTime;

    private Date appliedBeginDate;

    private Date appliedEndDate;

    private Boolean status;

    private Timestamp employTime;

    private CV cv;

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getAppliedBeginDate() {
        return appliedBeginDate;
    }

    public void setAppliedBeginDate(Date appliedBeginDate) {
        this.appliedBeginDate = appliedBeginDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getAppliedEndDate() {
        return appliedEndDate;
    }

    public void setAppliedEndDate(Date appliedEndDate) {
        this.appliedEndDate = appliedEndDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Timestamp getEmployTime() {
        return employTime;
    }

    public void setEmployTime(Timestamp employTime) {
        this.employTime = employTime;
    }

    public CV getCv() {
        return cv;
    }

    public void setCv(CV cv) {
        this.cv = cv;
    }
}
