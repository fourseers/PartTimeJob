package com.fourseers.parttimejob.arrangement.service;

import com.fourseers.parttimejob.arrangement.dto.ScheduleDto;
import com.fourseers.parttimejob.arrangement.projection.WorkNotifyProjection;
import com.fourseers.parttimejob.arrangement.projection.WorkProjection;
import com.fourseers.parttimejob.arrangement.projection.WorkStatusProjection;
import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface WorkService {

    Page<WorkProjection> findPageByUsername(String username, int pageCount, int pageSize);

    Page<WorkProjection> findPageByShopIdAndUsername(int shopId, String username, int pageCount, int pageSize);

    void remark(String username, int workId, int score);

    List<ScheduleDto> getSchedule(WechatUser user, LocalDate beginDate, LocalDate endDate);

    WorkStatusProjection getWorkStatus(String username, Integer shopId, Integer fromYear, Integer fromMonth, Integer toYear, Integer toMonth);

    List<WorkNotifyProjection> getNotCheckedIn();

    List<WorkNotifyProjection> getNotCheckedOut();
}
