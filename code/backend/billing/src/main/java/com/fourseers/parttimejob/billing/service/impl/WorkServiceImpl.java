package com.fourseers.parttimejob.billing.service.impl;

import com.fourseers.parttimejob.billing.dao.CompanyDao;
import com.fourseers.parttimejob.billing.dao.WorkDao;
import com.fourseers.parttimejob.billing.service.WorkService;
import com.fourseers.parttimejob.common.entity.Company;
import com.fourseers.parttimejob.common.entity.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WorkServiceImpl implements WorkService {

    @Autowired
    private WorkDao workDao;

    @Autowired
    private CompanyDao companyDao;

    @Override
    public void rejectByUserAndWorkId(String username, Integer workId) {
        Company company = companyDao.findByUsername(username);

        Work work = workDao.findByWorkId(workId);

        if (company == null) {
            throw new RuntimeException("user does not belong to any company");
        }

        if (work == null || !company.equals(work.getJob().getShop().getCompany())) {
            throw new RuntimeException("work not exist or not belong to current company");
        }

        if (work.getSalaryConfirmed()) {
            throw new RuntimeException("work already paid");
        }

        if (work.getRejected()) {
            throw new RuntimeException("work already rejected");
        }

        work.setRejected(true);
        workDao.save(work);
    }
}
