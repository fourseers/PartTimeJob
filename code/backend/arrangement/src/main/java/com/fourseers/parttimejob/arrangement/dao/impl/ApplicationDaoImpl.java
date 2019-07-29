package com.fourseers.parttimejob.arrangement.dao.impl;

import com.fourseers.parttimejob.arrangement.dao.ApplicationDao;
import com.fourseers.parttimejob.arrangement.projection.ApplicationProjection;
import com.fourseers.parttimejob.arrangement.repository.ApplicationRepository;
import com.fourseers.parttimejob.common.entity.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

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
}
