package com.fourseers.parttimejob.arrangement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Job {

    private Integer jobId;
    private String jobName;
    private Timestamp beginDate;
    private Timestamp endDate;
    private String jobDetail;
    private Integer needGender;
    private Integer needAmount;
    private Timestamp beginApplyDate;
    private Timestamp endApplyDate;
    private String education;
    private List<Tag> tagList;
    private Double salary;
    private Shop shop;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    @NotBlank
    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    @NotNull
    public Timestamp getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Timestamp beginDate) {
        this.beginDate = beginDate;
    }

    @NotNull
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @NotBlank
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
    public Timestamp getBeginApplyDate() {
        return beginApplyDate;
    }

    public void setBeginApplyDate(Timestamp beginApplyDate) {
        this.beginApplyDate = beginApplyDate;
    }

    @NotNull
    public Timestamp getEndApplyDate() {
        return endApplyDate;
    }

    public void setEndApplyDate(Timestamp endApplyDate) {
        this.endApplyDate = endApplyDate;
    }

    @NotBlank
    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
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

    @ManyToOne
    @JsonIgnore
    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
