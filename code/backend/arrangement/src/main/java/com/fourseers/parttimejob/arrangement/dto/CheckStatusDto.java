package com.fourseers.parttimejob.arrangement.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
public class CheckStatusDto {
    private Date workDate;
    private Time expectedCheckin;
    private Time expectedCheckout;
    private Time checkin;
    private Time checkout;
}
