package com.fourseers.parttimejob.arrangement.dao.impl;

import com.fourseers.parttimejob.arrangement.dao.JobDao;
import com.fourseers.parttimejob.arrangement.repository.JobRepository;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.Shop;
import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<Job> findByShop(Shop shop) {
        return jobRepository.findByShop(shop);
    }

    @Override
    public Page<Job> findJobs(WechatUser user, int pageCount, int pageSize) {
        PageRequest pageRequest = PageRequest.of(
                pageCount, pageSize, Sort.by("jobId").ascending());
        return jobRepository.findAll(pageRequest);
    }

    @Override
    public Page<Job> findJobsByGeoLocation(WechatUser user, float longitude, float latitude, int pageCount, int pageSize) {
        PageRequest pageRequest = PageRequest.of(
                pageCount, pageSize, Sort.by("dis").ascending());
        return jobRepository.findByGeoLocation(longitude, latitude, pageRequest);
    }
}
