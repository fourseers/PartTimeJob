package com.fourseers.parttimejob.common.entity;

import javax.persistence.*;

@Entity
public class Billing {

    private Integer billId;
    private Double payment;
    private Work work;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    @OneToOne
    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }
}
