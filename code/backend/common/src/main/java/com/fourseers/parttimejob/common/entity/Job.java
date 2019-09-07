package com.fourseers.parttimejob.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Job implements Cloneable {

    private Integer jobId;
    private String identifier;
    private String jobName;
    private Date beginDate;
    private Date endDate;
    private Time beginTime;
    private Time endTime;
    private String jobDetail;
    private Integer needGender;
    private Integer needAmount;
    private Integer appliedAmount = 0;
    private Timestamp beginApplyTime;
    private Timestamp endApplyTime;
    private Etc.Education education;
    private List<Tag> tagList;
    private Double salary;
    private Shop shop;
    private Boolean manualStop = false;
    private String identifier;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

//    @NotNull              // commented this out for test purposes
    @Column(length = 40)    // reserve a couple bits
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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
    public Time getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Time beginTime) {
        this.beginTime = beginTime;
    }

    @NotNull
    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @NotNull
    @Column(columnDefinition = "TEXT")
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
    public Timestamp getBeginApplyTime() {
        return beginApplyTime;
    }

    public void setBeginApplyTime(Timestamp beginApplyTime) {
        this.beginApplyTime = beginApplyTime;
    }

    @NotNull
    public Timestamp getEndApplyTime() {
        return endApplyTime;
    }

    public void setEndApplyTime(Timestamp endApplyTime) {
        this.endApplyTime = endApplyTime;
    }

    @NotNull
    @Convert(converter = Etc.EducationColumnConverter.class)
    public Etc.Education getEducation() {
        return education;
    }

    public void setEducation(Etc.Education education) {
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

    @Column(columnDefinition = "INTEGER DEFAULT 0")
    public Integer getAppliedAmount() {
        return appliedAmount;
    }

    public void setAppliedAmount(Integer appliedAmount) {
        this.appliedAmount = appliedAmount;
    }

    @NotNull
    public Boolean getManualStop() {
        return manualStop;
    }

    public void setManualStop(Boolean manualStop) {
        this.manualStop = manualStop;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Job clone() throws CloneNotSupportedException {
        return (Job) super.clone();
    }
}
