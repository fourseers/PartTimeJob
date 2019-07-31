package com.fourseers.parttimejob.arrangement.dto;

import java.util.Date;

public class ApplyUserEntryDto {
    public ApplyUserEntryDto(Integer applicationId, String cvId,
                             Integer jobId, Date createTime,
                             Date appliedBeginDate, Date appliedEndDate,
                             Boolean status, Date employTime) {
        this.applicationId = applicationId;
        this.cvId = cvId;
        this.jobId = jobId;
        this.createTime = createTime;
        this.appliedBeginDate = appliedBeginDate;
        this.appliedEndDate = appliedEndDate;
        this.status = status;
        this.employTime = employTime;
    }

    private Integer applicationId;
    private String cvId;
    private Integer jobId;
    private Date createTime;
    private Date appliedBeginDate;
    private Date appliedEndDate;
    private Boolean status;
    private Date employTime;

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public String getCvId() {
        return cvId;
    }

    public void setCvId(String cvId) {
        this.cvId = cvId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
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

    public Date getEmployTime() {
        return employTime;
    }

    public void setEmployTime(Date employTime) {
        this.employTime = employTime;
    }
}
