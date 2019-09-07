package com.fourseers.parttimejob.arrangement.projection;

import java.sql.Time;

public interface WorkNotifyProjection {
    String getUsername();
    String getJobname();
    String getPhone();
    Time getTime();
}
