package com.fourseers.parttimejob.arrangement.service.impl;

import com.fourseers.parttimejob.arrangement.dao.ApplicationDao;
import com.fourseers.parttimejob.arrangement.dao.CVDao;
import com.fourseers.parttimejob.arrangement.dao.JobDao;
import com.fourseers.parttimejob.arrangement.dao.MerchantUserDao;
import com.fourseers.parttimejob.arrangement.dto.AppliedTimeDto;
import com.fourseers.parttimejob.arrangement.dto.ApplyDto;
import com.fourseers.parttimejob.arrangement.projection.JobDetailedInfoProjection;
import com.fourseers.parttimejob.arrangement.service.JobService;
import com.fourseers.parttimejob.common.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class JobServiceImpl implements JobService {

    @Autowired
    JobDao jobDao;

    @Autowired
    MerchantUserDao merchantUserDao;

    @Autowired
    CVDao cvDao;

    @Autowired
    ApplicationDao applicationDao;

    @Value("${app.pagination.pageSize}")
    private int PAGE_SIZE;

    public void save(Job job, int shopId, String username) {

        MerchantUser merchantUser = merchantUserDao.findByUsername(username);
        Company company = merchantUser.getCompany();

        for (Shop shop : company.getShops()) {
            if (shop.getShopId().equals(shopId)) {
                job.setShop(shop);
                jobDao.save(job);
                return;
            }
        }

        throw new RuntimeException("shop not exist or not belong to");

    }

    public Job findByJobIdAndUsername(int jobId, String username) {
        MerchantUser user = merchantUserDao.findByUsername(username);

        if (user.getCompany() == null) {
            throw new RuntimeException("user does not belong to a company");
        }

        Job job = jobDao.findByJobId(jobId);
        if (job == null) {
            throw new RuntimeException("job not exist or not belong to");
        }
        for (Shop shop : user.getCompany().getShops()) {
            if (job.getShop().getShopId().equals(shop.getShopId())) {
                return job;
            }
        }

        throw new RuntimeException("job not exist or not belong to");
    }

    public Page<Job> findPageByShopIdAndUsername(int shopId, String username, int pageCount, int pageSize) {
        MerchantUser user = merchantUserDao.findByUsername(username);

        if (user.getCompany() == null) {
            throw new RuntimeException("user does not belong to a company");
        }
        for (Shop shop : user.getCompany().getShops()) {
            if (shop.getShopId() == shopId) {
                Page<Job> jobs = jobDao.findPageByShop(shop, pageCount, pageSize);
                if (jobs.isEmpty()) {
                    throw new RuntimeException("job not exist");
                }
                return jobs;
            }
        }

        throw new RuntimeException("shop not exist or not belong to");
    }

    public Page<Job> findPageByUsername(String username, int pageCount, int pageSize) {
        MerchantUser user = merchantUserDao.findByUsername(username);

        if (user.getCompany() == null) {
            throw new RuntimeException("user does not belong to a company");
        }

        return jobDao.findPageByCompany(user.getCompany(), pageCount, pageSize);

    }

    @Override
    public boolean apply(WechatUser user, ApplyDto applyDto) {
        Job job = jobDao.findByJobId(applyDto.getJobId());
        if(job == null)
            throw new RuntimeException("Invalid job.");
        if(job.getManualStop())
            throw new RuntimeException("Job recruit manually stopped by merchant.");
        Date currentTime = new Date();
        if(job.getBeginApplyTime().after(currentTime) || job.getEndApplyTime().before(currentTime))
            throw new RuntimeException("Sorry, you've missed the apply date.");

        Shop shop = job.getShop();
        CV cv = cvDao.getOne(applyDto.getCvId());
        if(cv == null)
            throw new RuntimeException("Invalid cv.");
        if(cv.getUserId() != user.getUserId())
            throw new RuntimeException("CV doesn't belong to user - check cv id.");
        if(job.getAppliedAmount() >= job.getNeedAmount()) {
            throw new RuntimeException("Sorry, no more seats available.");
        }
        if(job.getNeedGender() != 2 && (job.getNeedGender() == 1) != user.getGender()) {
            throw new RuntimeException("Sorry, this job requires a different gender.");
        }

//        if(user.getCity() != null && shop.getCity() != null) {
//            if(!user.getCity().equals(shop.getCity()))
//                throw new RuntimeException("User and shop are from different cities.");
//        }
        Etc.Education actualEdu = cv.getEducation();
        Etc.Education requiredEdu = job.getEducation();
        if(actualEdu == null || requiredEdu == null) {
            throw new RuntimeException("Invalid education");
        }
        if(!actualEdu.satisfies(requiredEdu)) {
            throw new RuntimeException("No enough education");
        }

        // check if user have already applied
        if(applicationDao.haveAlreadyApplied(user, job))
            throw new RuntimeException("You've already applied for that job.");

        // check if user is already occupied
        List<Application> occupied = applicationDao.findWithin(
                user, applyDto.getBeginDate(), applyDto.getEndDate(), job.getBeginTime(), job.getEndTime());
        if(occupied.size() > 0)
            throw new RuntimeException("You've already applied for a job during this time period.");

        List<Application> acceptedApplication = applicationDao.getAppliedByJob(job);
        for(Application app: acceptedApplication) {
            if(!(app.getAppliedBeginDate().toLocalDate().isAfter(applyDto.getEndDate().toLocalDate()) ||
                    app.getAppliedEndDate().toLocalDate().isBefore(applyDto.getBeginDate().toLocalDate())))
                throw new RuntimeException("Sorry, time already applied.");
        }

        // all checks completed
        Application application = new Application();
        application.setWechatUser(user);
        application.setCvId(applyDto.getCvId());
        application.setJob(job);
        application.setAppliedBeginDate(applyDto.getBeginDate());
        application.setAppliedEndDate(applyDto.getEndDate());
        application.setStatus(null);
        applicationDao.addOne(application);
        return true;
    }

    @Override
    public JobDetailedInfoProjection getJobDetail(int jobId) {
        JobDetailedInfoProjection projection = jobDao.getJobDetail(jobId);
        return projection;
    }

    @Override
    public AppliedTimeDto getJobAppliedTime(int jobId) {
        Job job = jobDao.findByJobId(jobId);
        if(job == null)
            throw new RuntimeException("Job does not exist.");
        List<Application> appliedApplications = applicationDao.getAppliedByJob(job);
        AppliedTimeDto ret = new AppliedTimeDto();
        List<AppliedTimeDto.DateTuple> dateTuples = new ArrayList<>();
        for(Application app: appliedApplications) {
            dateTuples.add(new AppliedTimeDto.DateTuple(
                    app.getAppliedBeginDate(), app.getAppliedEndDate()));
        }
        ret.setAppliedDates(dateTuples);
        ret.setJobId(jobId);
        return ret;
    }

    @Override
    public void setJobHiringState(Integer jobId, String username, Boolean stop) {
        Job job = findByJobIdAndUsername(jobId, username);

        job.setManualStop(stop);
        jobDao.save(job);
    }

    @Override
    public Page<Job> findJobs(WechatUser user, int pageCount) {
        return jobDao.findJobs(user, pageCount, PAGE_SIZE);
    }

    @Override
    public Page<Job> findJobsByGeoLocation(WechatUser user, BigDecimal longitude, BigDecimal latitude, int pageCount) {
        return jobDao.findJobsByGeoLocation(user, longitude, latitude, pageCount, PAGE_SIZE);
    }
}
