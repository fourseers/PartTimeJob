package com.fourseers.parttimejob.arrangement.service;

import com.fourseers.parttimejob.arrangement.dto.ApplyOutDto;
import com.fourseers.parttimejob.arrangement.dto.ApplyUserEntryDto;
import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.data.domain.Page;

public interface ApplicationService {

    Page<ApplyOutDto> getUnapprovedApplicationsByUsernameAndJobId(String username, Integer jobId, int pageCount, int pageSize);

    void rejectByUsernameAndApplicationId(String username, Integer applicationId);

    void acceptByUsernameAndApplicationId(String username, Integer applicationId);

    Page<ApplyUserEntryDto> getApplicationsByWechatUser(WechatUser user, int pageCount);
}
