package com.fourseers.parttimejob.arrangement.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ApplyDto {
    @NotNull
    Integer jobId;
    @NotBlank
    String cvId;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getCvId() {
        return cvId;
    }

    public void setCvId(String cvId) {
        this.cvId = cvId;
    }
}
