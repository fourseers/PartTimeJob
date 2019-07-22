package com.fourseers.parttimejob.common.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Billing {

    private Integer billId;
    private WechatUser employee;
    private Shop shop;
    private Job job;
    private Date workDate;
    private Boolean paid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @OneToOne
    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @ManyToOne
    public WechatUser getEmployee() {
        return employee;
    }

    public void setEmployee(WechatUser employee) {
        this.employee = employee;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }
}
