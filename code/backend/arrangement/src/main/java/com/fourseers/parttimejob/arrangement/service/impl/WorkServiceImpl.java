package com.fourseers.parttimejob.arrangement.service.impl;

import com.fourseers.parttimejob.arrangement.dao.ApplicationDao;
import com.fourseers.parttimejob.arrangement.dao.MerchantUserDao;
import com.fourseers.parttimejob.arrangement.dao.WorkDao;
import com.fourseers.parttimejob.arrangement.dto.ScheduleDto;
import com.fourseers.parttimejob.arrangement.projection.WorkNotifyProjection;
import com.fourseers.parttimejob.arrangement.projection.WorkProjection;
import com.fourseers.parttimejob.arrangement.projection.WorkStatusProjection;
import com.fourseers.parttimejob.arrangement.service.WorkService;
import com.fourseers.parttimejob.common.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class WorkServiceImpl implements WorkService {

    @Autowired
    private MerchantUserDao merchantUserDao;

    @Autowired
    private WorkDao workDao;

    @Autowired
    private ApplicationDao applicationDao;

    public Page<WorkProjection> findPageByUsername(String username, int pageCount, int pageSize) {
        MerchantUser user = merchantUserDao.findByUsername(username);

        if (user.getCompany() == null) {
            throw new RuntimeException("user does not belong to a company");
        }

        if (pageCount < 0) {
            throw new RuntimeException("incorrect param");
        }

        return workDao.findPageByCompany(user.getCompany(), pageCount, pageSize);
    }

    public Page<WorkProjection> findPageByShopIdAndUsername(int shopId, String username, int pageCount, int pageSize) {
        MerchantUser user = merchantUserDao.findByUsername(username);

        if (user.getCompany() == null) {
            throw new RuntimeException("user does not belong to a company");
        }

        if (pageCount < 0) {
            throw new RuntimeException("incorrect param");
        }
        for (Shop shop : user.getCompany().getShops()) {
            if (shop.getShopId() == shopId) {
                Page<WorkProjection> jobs = workDao.findPageByShop(shop, pageCount, pageSize);
                if (jobs.isEmpty()) {
                    throw new RuntimeException("work not exist");
                }
                return jobs;
            }
        }

        throw new RuntimeException("shop not exist or not belong to");
    }

    @Override
    public void remark(String username, int workId, int score) {
        MerchantUser user = merchantUserDao.findByUsername(username);

        if (user.getCompany() == null) {
            throw new RuntimeException("user does not belong to a company");
        }

        Work work = workDao.findById(workId);

        if (work == null || work.getJob().getShop().getCompany() != user.getCompany()) {
            throw new RuntimeException("work not exist or not managed by current user");
        }

        if (work.getScore() != null) {
            throw new RuntimeException("work already remarked");
        }

        work.setScore(score);
        workDao.save(work);
    }

    @Override
    public List<ScheduleDto> getSchedule(WechatUser user, LocalDate beginDate, LocalDate endDate) {
        List<Application> involvedApps = applicationDao.getAppliedByUserAndDate(user, beginDate, endDate);
        List<ScheduleDto> ret = new ArrayList<>();
        for(Application app: involvedApps) {
            ScheduleDto scheduleDto = new ScheduleDto();
            scheduleDto.setBeginDate(app.getAppliedBeginDate());
            scheduleDto.setEndDate(app.getAppliedEndDate());
            scheduleDto.setJobId(app.getJob().getJobId());
            scheduleDto.setIdentifier(app.getJob().getIdentifier());
            scheduleDto.setJobName(app.getJob().getJobName());
            scheduleDto.setShopId(app.getJob().getShop().getShopId());
            scheduleDto.setShopName(app.getJob().getShop().getShopName());
            scheduleDto.setBeginTime(app.getJob().getBeginTime());
            scheduleDto.setEndTime(app.getJob().getEndTime());
            ret.add(scheduleDto);
        }
        return ret;
    }

    @Override
    public WorkStatusProjection getWorkStatus(String username, Integer shopId, Integer fromYear, Integer fromMonth, Integer toYear, Integer toMonth) {

        MerchantUser user = merchantUserDao.findByUsername(username);

        if (user.getCompany() == null) {
            throw new RuntimeException("user does not belong to a company");
        }

        boolean shopOwnedByUser = false;
        for (Shop shop : user.getCompany().getShops()) {
            if (shop.getShopId().equals(shopId)) {
                shopOwnedByUser = true;
                break;
            }
        }

        if (!shopOwnedByUser) {
            throw new RuntimeException("shop not exist or not belong to");
        }

        Calendar aCalendar = Calendar.getInstance();
        aCalendar.set(Calendar.YEAR, fromYear);
        aCalendar.set(Calendar.MONTH, fromMonth - 1);
        aCalendar.set(Calendar.DAY_OF_MONTH, 1);
        Date from = new Date(aCalendar.getTime().getTime());
        aCalendar.set(Calendar.YEAR, toYear);
        aCalendar.set(Calendar.MONTH, toMonth - 1);
        aCalendar.set(Calendar.DAY_OF_MONTH, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date to = new Date(aCalendar.getTime().getTime());
        if (from.after(to)) {
            throw new RuntimeException("incorrect param");
        }

        return workDao.getWorkStatus(shopId, from, to);
    }

    @Override
    public List<WorkNotifyProjection> getNotCheckedIn() {
        return workDao.getNotCheckedIn();
    }

    @Override
    public List<WorkNotifyProjection> getNotCheckedOut() {
        return workDao.getNotCheckedOut();
    }

}
