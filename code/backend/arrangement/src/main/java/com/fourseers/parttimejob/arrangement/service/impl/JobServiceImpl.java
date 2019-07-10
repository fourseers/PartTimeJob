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

    public Job findByJobId(int jobId) {
        return jobDao.findByJobId(jobId);
    }
}
