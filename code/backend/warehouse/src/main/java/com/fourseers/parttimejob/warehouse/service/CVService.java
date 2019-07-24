package com.fourseers.parttimejob.warehouse.service;

import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.warehouse.dto.CVDto;
import com.fourseers.parttimejob.warehouse.dto.NewCVDto;
import com.fourseers.parttimejob.warehouse.projection.CVBriefProjection;

import java.util.List;

public interface CVService {

    public List<CVBriefProjection> getCVListByUser(WechatUser user);

    public CVDto getCV(String cvId);
    public boolean updateCV(CVDto cvDto, WechatUser user);
    public boolean addCV(NewCVDto cvDao, WechatUser user);
    public boolean deleteCV(String cvId, WechatUser user);
}
