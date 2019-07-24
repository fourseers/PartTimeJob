package com.fourseers.parttimejob.arrangement.service.impl;

import com.fourseers.parttimejob.arrangement.dao.MerchantUserDao;
import com.fourseers.parttimejob.arrangement.dao.WorkDao;
import com.fourseers.parttimejob.arrangement.projection.WorkProjection;
import com.fourseers.parttimejob.arrangement.service.WorkService;
import com.fourseers.parttimejob.common.entity.MerchantUser;
import com.fourseers.parttimejob.common.entity.Shop;
import com.fourseers.parttimejob.common.entity.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WorkServiceImpl implements WorkService {

    @Autowired
    private MerchantUserDao merchantUserDao;

    @Autowired
    private WorkDao workDao;

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

}
