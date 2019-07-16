package com.fourseers.parttimejob.common.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class DateInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Basic
    private Time checkinTime;

    @NotNull
    @Basic
    private Time checkoutTime;

    @NotNull
    private Date workDate;

    @ManyToOne
    private Job job;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Time getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(Time checkinTime) {
        this.checkinTime = checkinTime;
    }

    public Time getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(Time checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
