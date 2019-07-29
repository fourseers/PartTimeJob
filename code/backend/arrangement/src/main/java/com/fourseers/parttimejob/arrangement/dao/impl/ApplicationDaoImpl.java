package com.fourseers.parttimejob.arrangement.dao.impl;

import com.fourseers.parttimejob.arrangement.dao.ApplicationDao;
import com.fourseers.parttimejob.arrangement.projection.ApplicationProjection;
import com.fourseers.parttimejob.arrangement.repository.ApplicationRepository;
import com.fourseers.parttimejob.common.entity.Application;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ApplicationDaoImpl implements ApplicationDao {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public boolean addOne(Application application) {
        if(application.getApplicationId() != null &&
                applicationRepository.existsById(application.getApplicationId())) {
            return false;
        }
        applicationRepository.save(application);
        return true;
    }

    @Override
    public Page<ApplicationProjection> getApplicationsByJobId(Integer jobId, int pageCount, int pageSize) {
        Pageable pageable = PageRequest.of(pageCount, pageSize);
        return applicationRepository.getApplicationsByJobId(jobId, pageable);
    }

    @Override
    public List<Application> getAppliedByJob(Job job) {
        return applicationRepository.findApprovedByJob(job);
    }

    @Override
    public List<Application> getAppliedByUserAndDate(WechatUser user, LocalDate beginDate, LocalDate endDate) {
        return applicationRepository.findApprovedByUserAndDate(
                user, Date.valueOf(beginDate), Date.valueOf(endDate));
    }

    @Override
    public boolean haveAlreadyApplied(WechatUser user, Job job) {
        return applicationRepository.existsByWechatUserAndJob(user, job);
    }
}
