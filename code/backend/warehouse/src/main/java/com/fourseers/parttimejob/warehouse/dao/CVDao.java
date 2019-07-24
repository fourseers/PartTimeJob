package com.fourseers.parttimejob.warehouse.dao;

import com.fourseers.parttimejob.common.entity.WechatUser;
import com.fourseers.parttimejob.warehouse.dto.CVDto;
import com.fourseers.parttimejob.warehouse.dto.NewCVDto;
import com.fourseers.parttimejob.warehouse.projection.CVBriefProjection;

import java.util.List;

public interface CVDao {
    public List<CVBriefProjection> getCVListByUser(WechatUser user);

    public CVDto getOne(String cvId);
    public boolean updateOne(CVDto cvDto, WechatUser user);
    public boolean addOne(NewCVDto newCvDto, WechatUser user);
    public boolean deleteOne(String cvId);

    public boolean belongsTo(String cvId, WechatUser user);
}
