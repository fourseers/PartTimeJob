package com.fourseers.parttimejob.arrangement.projection;

import com.fourseers.parttimejob.common.entity.Etc;
import com.fourseers.parttimejob.common.entity.Tag;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public interface JobDetailedInfoProjection {

    Integer getJobId();
    String getIdentifier();
    String getJobName();
    Date getBeginDate();
    Date getEndDate();
    String getJobDetail();
    Integer getNeedGender();
    Integer getNeedAmount();
    Timestamp getBeginApplyTime();
    Timestamp getEndApplyTime();
    Time getBeginTime();
    Time getEndTime();
    Etc.Education getEducation();
    List<Tag> getTagList();
    Double getSalary();
    ShopBriefProjection getShop();
}