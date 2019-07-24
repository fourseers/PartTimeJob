package com.fourseers.parttimejob.warehouse.service.impl;

import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.warehouse.dao.CVDao;
import com.fourseers.parttimejob.warehouse.dto.CVDto;
import com.fourseers.parttimejob.warehouse.dto.NewCVDto;
import com.fourseers.parttimejob.warehouse.projection.CVBriefProjection;
import com.fourseers.parttimejob.warehouse.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CVServiceImpl implements CVService {

    @Autowired
    private CVDao cvDao;

    @Override
    public List<CVBriefProjection> getCVListByUser(WechatUser user) {
        if(user == null)
            throw new RuntimeException("Invalid user");
        return cvDao.getCVListByUser(user);
    }

    @Override
    public CVDto getCV(String cvId) {
        return cvDao.getOne(cvId);
    }

    @Override
    public boolean updateCV(CVDto cvDto, WechatUser user) {
        return cvDao.updateOne(cvDto, user);
    }

    @Override
    public boolean addCV(NewCVDto newCvDto, WechatUser user) {
        return cvDao.addOne(newCvDto, user);
    }

    @Override
    public boolean deleteCV(String cvId, WechatUser user) {
        if(!cvDao.belongsTo(cvId, user))
            throw new RuntimeException("CV doesn't belong to this user.");
        return cvDao.deleteOne(cvId);
    }
}
