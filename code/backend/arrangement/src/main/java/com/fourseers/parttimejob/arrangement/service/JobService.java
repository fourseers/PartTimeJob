package com.fourseers.parttimejob.arrangement.service;

import com.fourseers.parttimejob.arrangement.entity.Job;

public interface JobService {

    void save(Job job, int shopId, String username);

    Job findByJobId(int jobId);
}
