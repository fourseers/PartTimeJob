package com.fourseers.parttimejob.billing.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.fourseers.parttimejob.billing.service.MonthlyBillService;
import com.fourseers.parttimejob.common.entity.MonthlyBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/alipay")
public class AlipayCallbackController {

    @Value("${app.alipay_public_key}")
    private String ALIPAY_PUBLIC_KEY;

    @Autowired
    private MonthlyBillService monthlyBillService;

    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    public String paymentStateNotify(@RequestParam Map<String, String> parameters) {
        boolean signVerified;
        try {
            signVerified = AlipaySignature.rsaCheckV1(parameters, ALIPAY_PUBLIC_KEY, "utf-8", "RSA2");
        } catch (AlipayApiException ex) {
            return "failure";
        }

        if (!signVerified) {
            return "failure";
        }

        MonthlyBill monthlyBill = monthlyBillService.findByMeta(parameters.get("out_trade_no"));
        if (monthlyBill == null) {
            return "failure";
        }

        if (!monthlyBill.getStatus().equals("success")) {
            monthlyBill.setStatus("paid");
            monthlyBillService.save(monthlyBill);
        }

        return "success";
    }
}
