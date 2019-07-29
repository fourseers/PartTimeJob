package com.fourseers.parttimejob.arrangement.dao.impl;

import java.sql.Date;
import java.sql.Time;

public class CheckStatusDto {
    private Date workDate;
    private Time expectedCheckin;
    private Time expectedCheckout;
    private Time checkin;
    private Time checkout;

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Time getExpectedCheckin() {
        return expectedCheckin;
    }

    public void setExpectedCheckin(Time expectedCheckin) {
        this.expectedCheckin = expectedCheckin;
    }

    public Time getExpectedCheckout() {
        return expectedCheckout;
    }

    public void setExpectedCheckout(Time expectedCheckout) {
        this.expectedCheckout = expectedCheckout;
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
}
