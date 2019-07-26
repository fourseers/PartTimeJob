package com.fourseers.parttimejob.arrangement.service;

import com.fourseers.parttimejob.arrangement.dto.CheckinDto;
import com.fourseers.parttimejob.arrangement.dto.CheckoutDto;
import com.fourseers.parttimejob.common.entity.WechatUser;

public interface CheckService {
    boolean checkIn(WechatUser user, CheckinDto params);
    boolean checkOut(WechatUser user, CheckoutDto params);
}