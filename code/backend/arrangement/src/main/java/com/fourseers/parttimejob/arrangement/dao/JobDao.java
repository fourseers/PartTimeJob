package com.fourseers.parttimejob.arrangement.dao;

import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.Shop;

import java.util.List;

public interface JobDao {
    void save(Job job);

    Job findByJobId(int jobId);

    List<Job> findByShop(Shop shop);
}
