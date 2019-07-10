package com.fourseers.parttimejob.arrangement.dao;

import com.fourseers.parttimejob.arrangement.entity.Job;

public interface JobDao {
    void save(Job job);

    Job findByJobId(int jobId);
}
