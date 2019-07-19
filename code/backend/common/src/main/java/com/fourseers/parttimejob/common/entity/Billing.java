package com.fourseers.parttimejob.common.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "BILLING")
public class Billing {

    private Integer billId;
    private WechatUser employee;
    private Company company;
    private Date workDate;
    private Double payment;

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

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @OneToOne
    public WechatUser getEmployee() {
        return employee;
    }

    public void setEmployee(WechatUser employee) {
        this.employee = employee;
    }
}
