package com.fourseers.parttimejob.arrangement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.util.List;

public class AppliedTimeDto {

    private Integer jobId;
    private List<DateTuple> appliedDates;

    public static class DateTuple {
        private Date beginDate;
        private Date endDate;

        public DateTuple(Date beginDate, Date endDate) {
            this.beginDate = beginDate;
            this.endDate = endDate;
        }

        @JsonFormat(shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd", timezone = "GMT+8")
        public Date getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(Date beginDate) {
            this.beginDate = beginDate;
        }

        @JsonFormat(shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd", timezone = "GMT+8")
        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public List<DateTuple> getAppliedDates() {
        return appliedDates;
    }

    public void setAppliedDates(List<DateTuple> appliedDates) {
        this.appliedDates = appliedDates;
    }
}
