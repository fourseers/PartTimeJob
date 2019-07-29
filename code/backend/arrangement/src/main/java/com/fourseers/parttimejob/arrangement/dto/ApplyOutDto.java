package com.fourseers.parttimejob.arrangement.dto;

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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Date getAppliedBeginDate() {
        return appliedBeginDate;
    }

    public void setAppliedBeginDate(Date appliedBeginDate) {
        this.appliedBeginDate = appliedBeginDate;
    }

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
