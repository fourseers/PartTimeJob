package com.fourseers.parttimejob.arrangement.dao;

import com.fourseers.parttimejob.arrangement.projection.ApplicationProjection;
import com.fourseers.parttimejob.common.entity.Application;
import org.springframework.data.domain.Page;

public interface ApplicationDao {

    boolean addOne(Application application);

    Page<ApplicationProjection> getApplicationsByJobId(Integer jobId, int pageCount, int pageSize);
}
