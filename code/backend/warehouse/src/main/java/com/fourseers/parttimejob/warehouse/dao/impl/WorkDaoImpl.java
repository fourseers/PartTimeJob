package com.fourseers.parttimejob.warehouse.dao.impl;

import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.common.entity.Work;
import com.fourseers.parttimejob.warehouse.dao.WorkDao;
import com.fourseers.parttimejob.warehouse.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkDaoImpl implements WorkDao {

    @Autowired
    private WorkRepository workRepository;

    @Override
    public boolean haveWorkedIn(WechatUser user, Integer shopId) {
        List<Work> works =  workRepository.getByWorkerAndShop(user, shopId);
        return works.size() > 0;
    }
}
