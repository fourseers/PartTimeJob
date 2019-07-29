package com.fourseers.parttimejob.arrangement.service.impl;

import com.fourseers.parttimejob.arrangement.dao.JobDao;
import com.fourseers.parttimejob.arrangement.dao.WorkDao;
import com.fourseers.parttimejob.arrangement.dao.impl.CheckStatusDto;
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
import org.modelmapper.ModelMapper;
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

    @Autowired
    private WorkDao workDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CheckStatusDto getCheckStatusForToday(WechatUser user, Integer jobId) {
        Job job = jobDao.findByJobId(jobId);
        if(job == null)
            throw new RuntimeException("Invalid job.");
        Work work = workDao.findTodayByUserAndJob(user, job);
        if(work == null)
            throw new RuntimeException("User not working today.");
        return modelMapper.map(work, CheckStatusDto.class);
    }

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


        // get work entity
        Work work = workRepository.getByJobAndWorker(job, user);
        if(work != null) {
            if (work.getCheckin() != null)
                throw new RuntimeException("Already checked in.");
            if(!TimeUtil.withIn(work.getExpectedCheckin().toLocalTime()))
                throw new RuntimeException("Not within checkin time.");
        } else
            throw new RuntimeException("User doesn't work today at this job.");
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

        // old work entity
        Work work = workRepository.getByJobAndWorker(job, user);
        if(work == null)
            throw new RuntimeException("User doesn't work today at this job.");
        if(work.getCheckin() == null)
            throw new RuntimeException("Haven't checked in yet.");
        if(work.getCheckout() != null)
            throw new RuntimeException("Already checked out.");
        if(!TimeUtil.withIn(work.getExpectedCheckin().toLocalTime()))
            throw new RuntimeException("Not within checkout time.");
        work.setCheckout(Time.valueOf(LocalTime.now()));
        workRepository.save(work);
        return true;
    }
}
