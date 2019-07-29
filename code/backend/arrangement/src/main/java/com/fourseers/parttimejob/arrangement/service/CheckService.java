package com.fourseers.parttimejob.arrangement.service;

import com.fourseers.parttimejob.arrangement.dao.impl.CheckStatusDto;
import com.fourseers.parttimejob.arrangement.dto.CheckinDto;
import com.fourseers.parttimejob.arrangement.dto.CheckoutDto;
import com.fourseers.parttimejob.common.entity.WechatUser;

public interface CheckService {
    CheckStatusDto getCheckStatusForToday(WechatUser user, Integer jobId);
    boolean checkIn(WechatUser user, CheckinDto params);
    boolean checkOut(WechatUser user, CheckoutDto params);
}
