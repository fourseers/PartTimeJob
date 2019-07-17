package com.fourseers.parttimejob.arrangement.service.impl;

import com.fourseers.parttimejob.arrangement.dao.JobDao;
import com.fourseers.parttimejob.arrangement.dao.MerchantUserDao;
import com.fourseers.parttimejob.arrangement.service.JobService;
import com.fourseers.parttimejob.common.entity.Company;
import com.fourseers.parttimejob.common.entity.Job;
import com.fourseers.parttimejob.common.entity.MerchantUser;
import com.fourseers.parttimejob.common.entity.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
