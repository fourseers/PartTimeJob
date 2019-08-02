package com.fourseers.parttimejob.arrangement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public class JobDto {

    private Integer shopId;
    private String jobName;
    private Date beginDate;
    private Date endDate;
    private Time beginTime;
    private Time endTime;
    private String jobDetail;
    private Integer needGender;
    private Integer needAmount;
    private Timestamp beginApplyTime;
    private Timestamp endApplyTime;
    private String education;
    private List<Integer> tagList;
    private Double salary;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    @NotBlank
    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    @NotNull
    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @NotNull
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "GMT+8")
    public Time getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Time beginTime) {
        this.beginTime = beginTime;
    }

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "GMT+8")
    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @NotNull
    public String getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(String jobDetail) {
        this.jobDetail = jobDetail;
    }

    @NotNull
    @Min(value = 0)
    @Max(value = 2)
    public Integer getNeedGender() {
        return needGender;
    }

    public void setNeedGender(Integer needGender) {
        this.needGender = needGender;
    }

    @NotNull
    @Min(value = 0)
    public Integer getNeedAmount() {
        return needAmount;
    }

    public void setNeedAmount(Integer needAmount) {
        this.needAmount = needAmount;
    }

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Timestamp getBeginApplyTime() {
        return beginApplyTime;
    }

    public void setBeginApplyTime(Timestamp beginApplyTime) {
        this.beginApplyTime = beginApplyTime;
    }

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Timestamp getEndApplyTime() {
        return endApplyTime;
    }

    public void setEndApplyTime(Timestamp endApplyTime) {
        this.endApplyTime = endApplyTime;
    }

    @NotBlank
    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @NotNull
    public List<Integer> getTagList() {
        return tagList;
    }

    public void setTagList(List<Integer> tagList) {
        this.tagList = tagList;
    }

    @NotNull
    @Min(value = 0)
    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
