package com.fourseers.parttimejob.arrangement.dao;

import com.fourseers.parttimejob.arrangement.entity.Job;
import com.fourseers.parttimejob.arrangement.entity.Shop;

import java.util.List;

public interface JobDao {
    void save(Job job);

    Job findByJobId(int jobId);

    List<Job> findByShop(Shop shop);
}
