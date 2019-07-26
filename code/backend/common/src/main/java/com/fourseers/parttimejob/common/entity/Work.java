package com.fourseers.parttimejob.common.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.sql.Time;
import java.sql.Date;

@Entity
public class Work {
    private Integer workId;
    private Date workDate;
    private Integer score;
    private Time checkin;
    private Time checkout;
    private String log;
    private Boolean salaryConfirmed = false;
    private Boolean rejected = false;
    private WechatUser worker;
    private Job job;
    private Billing billing;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    @Min(value = 0)
    @Max(value = 5)
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Time getCheckin() {
        return checkin;
    }

    public void setCheckin(Time checkin) {
        this.checkin = checkin;
    }

    public Time getCheckout() {
        return checkout;
    }

    public void setCheckout(Time checkout) {
        this.checkout = checkout;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Boolean getSalaryConfirmed() {
        return salaryConfirmed;
    }

    public void setSalaryConfirmed(Boolean salaryConfirmed) {
        this.salaryConfirmed = salaryConfirmed;
    }

    public Boolean getRejected() {
        return rejected;
    }

    public void setRejected(Boolean rejected) {
        this.rejected = rejected;
    }

    @ManyToOne
    public WechatUser getWorker() {
        return worker;
    }

    public void setWorker(WechatUser worker) {
        this.worker = worker;
    }

    @ManyToOne
    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @OneToOne
    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }
}
