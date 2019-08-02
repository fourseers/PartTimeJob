package com.fourseers.parttimejob.arrangement.dao.impl;

import com.fourseers.parttimejob.arrangement.dao.JobDao;
import com.fourseers.parttimejob.arrangement.projection.JobDetailedInfoProjection;
import com.fourseers.parttimejob.arrangement.repository.JobRepository;
import com.fourseers.parttimejob.common.entity.Company;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.Shop;
import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class JobDaoImpl implements JobDao {

    @Autowired
    private JobRepository jobRepository;

    public void save(Job job) {
        jobRepository.save(job);
    }

    public Job findByJobId(int jobId) {
        return jobRepository.findByJobId(jobId);
    }

    public Page<Job> findPageByShop(Shop shop, int pageCount, int pageSize) {
        Pageable pageable = PageRequest.of(pageCount, pageSize);
        return jobRepository.findPageByShopOrderByJobIdDesc(shop, pageable);
    }

    public Page<Job> findPageByCompany(Company company, int pageCount, int pageSize) {
        Pageable pageable = PageRequest.of(pageCount, pageSize);
        return jobRepository.findPageByCompany(company, pageable);
    }

    @Override
    public JobDetailedInfoProjection getJobDetail(int jobId) {
        return jobRepository.findJobByJobId(jobId);
    }

    @Override
    public Page<Job> findJobs(WechatUser user, int pageCount, int pageSize) {
        PageRequest pageRequest = PageRequest.of(
                pageCount, pageSize, Sort.by("jobId").ascending());
        return jobRepository.findAll(pageRequest);
    }

    @Override
    public Page<Job> findJobsByGeoLocation(WechatUser user, BigDecimal longitude, BigDecimal latitude, int pageCount, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageCount, pageSize);
        return jobRepository.findByGeoLocation(longitude, latitude, pageRequest);
    }
}
