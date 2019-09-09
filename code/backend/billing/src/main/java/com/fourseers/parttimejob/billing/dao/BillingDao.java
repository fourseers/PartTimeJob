package com.fourseers.parttimejob.billing.dao;

import com.fourseers.parttimejob.billing.projection.BillingStatusProjection;
import com.fourseers.parttimejob.common.entity.Billing;
import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.data.domain.Page;

import java.sql.Date;
import java.util.List;

public interface BillingDao {

    void save(Billing billing);
    void saveAll(List<Billing> billings);

    Double getBillingAmountByCompanyIdInGivenPeriod(Integer companyId, Date from, Date to);

    List<BillingStatusProjection> getBillingStatus(Integer companyId, Date from, Date to);

    Double getUserBalance(WechatUser user);

    Page<Billing> getBillingsDrawn(WechatUser user, Integer pageCount, Integer pageSize);

    List<Billing> getBillingsToDraw(WechatUser user);
}
