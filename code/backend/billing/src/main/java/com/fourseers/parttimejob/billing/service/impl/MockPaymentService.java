package com.fourseers.parttimejob.billing.service.impl;

import com.fourseers.parttimejob.billing.service.PayService;
import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

/**
 * mock implementation of pay service.
 * This service would always success when the beneficiary is valid,
 * regardless of the amount.
 * `Meta` field is a random uuid.
 */
@Service
public class MockPaymentService implements PayService {

    @Override
    public String getMethod() {
        return "mock";
    }

    @Override
    public PaymentResult pay(WechatUser user, Double payment) {
        PaymentResult result = new PaymentResult();
        result.setSuccess(true);
        result.setMessage("Success payment from mock payment client.");
        result.setMeta(UUID.randomUUID().toString());
        return result;
    }
}
