package com.fourseers.parttimejob.common.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applicationId;

    @ManyToOne
    private WechatUser wechatUser;

    @NotNull
    private String cvId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Job job;

    @CreationTimestamp
    private Timestamp createTime;

    private Date appliedBeginDate;

    private Date appliedEndDate;

    private Boolean status;

    private Timestamp employTime;

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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
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

    public Timestamp getEmployTime() {
        return employTime;
    }

    public void setEmployTime(Timestamp employTime) {
        this.employTime = employTime;
    }

    public WechatUser getWechatUser() {
        return wechatUser;
    }

    public void setWechatUser(WechatUser wechatUser) {
        this.wechatUser = wechatUser;
    }

    public String getCvId() {
        return cvId;
    }

    public void setCvId(String cvId) {
        this.cvId = cvId;
    }
}
