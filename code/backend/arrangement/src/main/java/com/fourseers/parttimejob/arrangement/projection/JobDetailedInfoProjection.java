package com.fourseers.parttimejob.arrangement.projection;

import com.fourseers.parttimejob.common.entity.Tag;

import java.sql.Timestamp;
import java.util.List;

public interface JobDetailedInfoProjection {

    Integer getJobId();
    String getJobName();
    Timestamp getBeginDate();
    Timestamp getEndDate();
    String getJobDetail();
    Integer getNeedGender();
    Integer getNeedAmount();
    Timestamp getBeginApplyDate();
    Timestamp getEndApplyDate();
    String getEducation();
    List<Tag> getTagList();
    Double getSalary();
    ShopBriefProjection getShop();
}