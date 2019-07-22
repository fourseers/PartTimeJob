package com.fourseers.parttimejob.arrangement.service.impl;

import com.fourseers.parttimejob.arrangement.dao.ApplicationDao;
import com.fourseers.parttimejob.arrangement.dao.CVDao;
import com.fourseers.parttimejob.arrangement.dao.JobDao;
import com.fourseers.parttimejob.arrangement.dao.MerchantUserDao;
import com.fourseers.parttimejob.arrangement.repository.CVRepository;
import com.fourseers.parttimejob.arrangement.projection.JobDetailedInfoProjection;
import com.fourseers.parttimejob.arrangement.service.JobService;
import com.fourseers.parttimejob.common.entity.*;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Page;
import org.springframework.jca.cci.RecordTypeNotSupportedException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.HTMLDocument;
import javax.transaction.Transactional;
import java.awt.dnd.DropTarget;
import java.util.Date;

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
    public boolean apply(WechatUser user, int jobId, String cvId) {
        Job job = jobDao.findByJobId(jobId);
        if(job == null)
            throw new RuntimeException("Invalid job.");
        Date currentTime = new Date();
        if(job.getBeginApplyDate().after(currentTime) || job.getEndApplyDate().before(currentTime))
            throw new RuntimeException("Sorry, you've missed the apply date.");

        Shop shop = job.getShop();
        CV cv = cvDao.getOne(cvId);
        if(cv == null)
            throw new RuntimeException("Invalid cv.");
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
        Etc.Education actualEdu = Etc.Education.fromName(cv.getEducation());
        Etc.Education requiredEdu = Etc.Education.fromName(job.getEducation());
        if(actualEdu == null || requiredEdu == null) {
            throw new RuntimeException("Invalid education");
        }
        if(actualEdu.ordinal() < requiredEdu.ordinal()) {
            throw new RuntimeException("No enough education");
        }

        // all checks completed
        Application application = new Application();
        application.setWechatUser(user);
        application.setCvId(cvId);
        application.setStatus(null);
        applicationDao.addOne(application);
        return true;
    }

    @Override
    public JobDetailedInfoProjection getJobDetail(int jobId) {
        return jobDao.getJobDetail(jobId);
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
    public Page<Job> findJobsByGeoLocation(WechatUser user, float longitude, float latitude, int pageCount) {
        return jobDao.findJobsByGeoLocation(user, longitude, latitude, pageCount, PAGE_SIZE);
    }
}
