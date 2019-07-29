package com.fourseers.parttimejob.arrangement.projection;

import java.sql.Date;
import java.sql.Timestamp;

public interface ApplicationProjection {

    Integer getApplicationId();
    Timestamp getCreateTime();
    Date getAppliedBeginDate();
    Date getAppliedEndDate();
    Boolean getStatus();
    Timestamp getEmployTime();
    String getCvId();
}
