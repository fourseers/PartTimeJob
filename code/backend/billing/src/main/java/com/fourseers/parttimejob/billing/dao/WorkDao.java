package com.fourseers.parttimejob.billing.dao;

import com.fourseers.parttimejob.billing.projection.UserWorkEntryProjection;
import com.fourseers.parttimejob.billing.projection.WorkBillingProjection;
import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.common.entity.Work;
import org.springframework.data.domain.Page;

import java.sql.Date;

public interface WorkDao {

    Page<WorkBillingProjection> getBillingsByCompanyIdOrderByBillIdDescInGivenPeriod(Integer companyId, Date fromDate, Date toDate, int pageCount, int pageSize);

    Work findByWorkId(Integer workId);

    void save(Work work);

    Page<UserWorkEntryProjection> getUserWorkAndBill(WechatUser user, Integer pageCount, Integer pageSize);
}
