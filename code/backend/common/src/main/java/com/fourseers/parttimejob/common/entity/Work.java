package com.fourseers.parttimejob.common.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Work {
    private Integer workId;
    private Date workDate;
    private Integer rank;
    private Timestamp checkin;
    private Timestamp checkout;
    private String log;
    private Boolean salaryConfirmed = false;
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

    @Null
    @Min(value = 0)
    @Max(value = 5)
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Null
    public Timestamp getCheckin() {
        return checkin;
    }

    public void setCheckin(Timestamp checkin) {
        this.checkin = checkin;
    }

    @Null
    public Timestamp getCheckout() {
        return checkout;
    }

    public void setCheckout(Timestamp checkout) {
        this.checkout = checkout;
    }

    @Null
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
