package com.fourseers.parttimejob.billing.service;

public interface WorkService {

    void rejectByUserAndWorkId(String username, Integer workId);
}
