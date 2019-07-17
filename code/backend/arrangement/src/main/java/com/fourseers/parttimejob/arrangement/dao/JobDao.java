package com.fourseers.parttimejob.arrangement.dao;

import com.fourseers.parttimejob.common.entity.Company;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.Shop;
import org.springframework.data.domain.Page;

public interface JobDao {
    void save(Job job);

    Job findByJobId(int jobId);

    Page<Job> findPageByShop(Shop shop, int pageCount, int pageSize);

    Page<Job> findPageByCompany(Company company, int pageCount, int pageSize);
}
