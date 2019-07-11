package com.fourseers.parttimejob.arrangement.service.impl;

import com.fourseers.parttimejob.arrangement.dao.JobDao;
import com.fourseers.parttimejob.arrangement.dao.MerchantUserDao;
import com.fourseers.parttimejob.arrangement.entity.Company;
import com.fourseers.parttimejob.arrangement.entity.Job;
import com.fourseers.parttimejob.arrangement.entity.MerchantUser;
import com.fourseers.parttimejob.arrangement.entity.Shop;
import com.fourseers.parttimejob.arrangement.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class JobServiceImpl implements JobService {

    @Autowired
    JobDao jobDao;

    @Autowired
    MerchantUserDao merchantUserDao;

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

    public List<Job> findByShopIdAndUsername(int shopId, String username) {
        MerchantUser user = merchantUserDao.findByUsername(username);

        if (user.getCompany() == null) {
            throw new RuntimeException("user does not belong to a company");
        }
        for (Shop shop : user.getCompany().getShops()) {
            if (shop.getShopId() == shopId) {
                List<Job> jobs = jobDao.findByShop(shop);
                if (jobs.size() == 0) {
                    throw new RuntimeException("job not exist");
                }
                return jobs;
            }
        }

        throw new RuntimeException("shop not exist or not belong to");
    }

    public List<Job> findByUsername(String username) {
        MerchantUser user = merchantUserDao.findByUsername(username);

        if (user.getCompany() == null) {
            throw new RuntimeException("user does not belong to a company");
        }

        List<Job> jobs = new ArrayList<>();

        for (Shop shop : user.getCompany().getShops()) {
            jobs.addAll(jobDao.findByShop(shop));
        }

        if (jobs.size() == 0) {
            throw new RuntimeException("job not exist");
        }

        return jobs;

    }
}