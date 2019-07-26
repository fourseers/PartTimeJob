package com.fourseers.parttimejob.billing.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/alipay")
public class AliPayCallbackController {

    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    public String paymentStateNotify(@RequestParam Map<String, String> parameters) {
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }
        return "success";
    }
}
