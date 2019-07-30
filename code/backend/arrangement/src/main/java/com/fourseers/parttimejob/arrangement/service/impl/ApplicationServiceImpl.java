package com.fourseers.parttimejob.arrangement.service.impl;

import com.fourseers.parttimejob.arrangement.dao.*;
import com.fourseers.parttimejob.arrangement.dto.ApplyOutDto;
import com.fourseers.parttimejob.arrangement.projection.ApplicationProjection;
import com.fourseers.parttimejob.arrangement.service.ApplicationService;
import com.fourseers.parttimejob.common.entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Timestamp;

@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationDao applicationDao;

    @Autowired
    private JobDao jobDao;

    @Autowired
    private MerchantUserDao merchantUserDao;

    @Autowired
    private CVDao cvDao;

    @Autowired
    private WorkDao workDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<ApplyOutDto> getApplicationsByUsernameAndJobId(String username, Integer jobId, int pageCount, int pageSize) {
        MerchantUser user = merchantUserDao.findByUsername(username);

        if (user.getCompany() == null) {
            throw new RuntimeException("user does not belong to a company");
        }

        Job job = jobDao.findByJobId(jobId);

        if (job == null || job.getShop().getCompany() != user.getCompany()) {
            throw new RuntimeException("job not exist or not belong to");
        }

        Page<ApplicationProjection> applicationProjections = applicationDao.getApplicationsByJobId(jobId, pageCount, pageSize);

        if (applicationProjections.isEmpty()) {
            throw new RuntimeException("application not exist");
        }

        return applicationProjections.map(applicationProjection -> {
            ApplyOutDto applyOutDto = modelMapper.map(applicationProjection, ApplyOutDto.class);
            CV cv = cvDao.getOne(applicationProjection.getCvId());
            applyOutDto.setCv(cv);
            return applyOutDto;
        });
    }

    @Override
    public void rejectByUsernameAndApplicationId(String username, Integer applicationId) {
        MerchantUser user = merchantUserDao.findByUsername(username);

        if (user.getCompany() == null) {
            throw new RuntimeException("user does not belong to a company");
        }

        Application application = applicationDao.findByApplicationId(applicationId);

        if (application == null || application.getJob().getShop().getCompany() != user.getCompany()) {
            throw new RuntimeException("application not exist or not belong to");
        }

        if (application.getStatus() != null) {
            throw new RuntimeException("application already processed");
        }
        application.setStatus(false);
        applicationDao.update(application);
    }

    @Override
    public void acceptByUsernameAndApplicationId(String username, Integer applicationId) {
        MerchantUser user = merchantUserDao.findByUsername(username);

        if (user.getCompany() == null) {
            throw new RuntimeException("user does not belong to a company");
        }

        Application application = applicationDao.findByApplicationId(applicationId);

        if (application == null || application.getJob().getShop().getCompany() != user.getCompany()) {
            throw new RuntimeException("application not exist or not belong to");
        }

        if (application.getStatus() != null) {
            throw new RuntimeException("application already processed");
        }
        application.setStatus(true);
        application.setEmployTime(new Timestamp(System.currentTimeMillis()));
        applicationDao.update(application);
        Date beginDate = application.getAppliedBeginDate();
        Date endDate = application.getAppliedEndDate();

        for (Date date = beginDate; !date.after(endDate); date = new Date(date.getTime() + 24 * 60 * 60 * 1000)) {
            Work work = new Work();
            Job job = application.getJob();
            WechatUser worker = application.getWechatUser();
            work.setJob(job);
            work.setExpectedCheckin(job.getBeginTime());
            work.setExpectedCheckout(job.getEndTime());
            work.setRejected(false);
            work.setSalaryConfirmed(false);
            work.setWorker(worker);
            work.setWorkDate(date);
            workDao.save(work);
        }
    }
}
