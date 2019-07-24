package com.fourseers.parttimejob.arrangement.service.impl;

import com.fourseers.parttimejob.arrangement.dao.JobDao;
import com.fourseers.parttimejob.arrangement.dto.CheckinDto;
import com.fourseers.parttimejob.arrangement.dto.CheckoutDto;
import com.fourseers.parttimejob.arrangement.repository.ShopRepository;
import com.fourseers.parttimejob.arrangement.repository.WorkRepository;
import com.fourseers.parttimejob.arrangement.service.CheckService;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.Shop;
import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.common.entity.Work;
import com.fourseers.parttimejob.common.util.GeoUtil;
import com.fourseers.parttimejob.common.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class CheckServiceImpl implements CheckService {

    @Autowired
    private JobDao jobDao;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private WorkRepository workRepository;

    @Override
    public boolean checkIn(WechatUser user, CheckinDto params) {
        Job job = jobDao.findByJobId(params.getJobId());
        if(job == null)
            throw new RuntimeException("Invalid job.");
        Shop shop = job.getShop();
        GeoUtil.Point userPos = new GeoUtil.Point(
                params.getLongitude(), params.getLatitude());
        GeoUtil.Point shopPos = new GeoUtil.Point(
                shop.getLongitude(), shop.getLatitude());

        if(!GeoUtil.within(userPos, shopPos))
            throw new RuntimeException("User too far from check in location.");

        if(!TimeUtil.withIn(job.getBeginTime().toLocalTime()))
            throw new RuntimeException("Not within checkin time.");

        // new work entity
        Work work = workRepository.getByJobAndWorker(job, user);
        if(work != null) {
            if (work.getCheckin() != null)
                throw new RuntimeException("Already checked in.");
        } else
            work = new Work();
        work.setWorkDate(Date.valueOf(LocalDate.now()));
        work.setCheckin(Time.valueOf(LocalTime.now()));
        work.setJob(job);
        work.setWorker(user);
        workRepository.save(work);
        return true;
    }

    @Override
    public boolean checkOut(WechatUser user, CheckoutDto params) {
        Job job = jobDao.findByJobId(params.getJobId());
        if(job == null)
            throw new RuntimeException("Invalid job.");
        Shop shop = job.getShop();
        GeoUtil.Point userPos = new GeoUtil.Point(
                params.getLongitude(), params.getLatitude());
        GeoUtil.Point shopPos = new GeoUtil.Point(
                shop.getLongitude(), shop.getLatitude());

        if(!GeoUtil.within(userPos, shopPos))
            throw new RuntimeException("User too far from check in location.");

        if(!TimeUtil.withIn(job.getEndTime().toLocalTime()))
            throw new RuntimeException("Not within checkin time.");

        // old work entity
        Work work = workRepository.getByJobAndWorker(job, user);
        if(work == null || work.getCheckin() == null)  {
            throw new RuntimeException("Haven't checked in yet.");
        }
        if(work.getCheckout() != null) {
            throw new RuntimeException("Already checked out.");
        }
        work.setCheckout(Time.valueOf(LocalTime.now()));
        workRepository.save(work);
        return true;
    }
}
