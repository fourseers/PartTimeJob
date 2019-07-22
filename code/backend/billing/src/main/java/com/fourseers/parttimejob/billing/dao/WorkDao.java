package com.fourseers.parttimejob.billing.dao;

import com.fourseers.parttimejob.billing.projection.WorkBillingProjection;
import com.fourseers.parttimejob.common.entity.Work;
import org.springframework.data.domain.Page;

public interface WorkDao {

    Page<WorkBillingProjection> getBillingsByCompanyIdOrderByBillIdDesc(Integer companyId, int pageCount, int pageSize);

    Work findByWorkId(Integer workId);

    void save(Work work);
}
