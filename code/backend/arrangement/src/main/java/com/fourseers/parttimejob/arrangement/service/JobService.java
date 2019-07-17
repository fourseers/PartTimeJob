package com.fourseers.parttimejob.arrangement.service;

import com.fourseers.parttimejob.common.entity.Job;
import org.springframework.data.domain.Page;

public interface JobService {

    void save(Job job, int shopId, String username);

    Job findByJobIdAndUsername(int jobId, String username);

    Page<Job> findPageByShopIdAndUsername(int shopId, String username, int pageCount, int pageSize);

    Page<Job> findPageByUsername(String username, int pageCount, int pageSize);
}
