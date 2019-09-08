package com.fourseers.parttimejob.arrangement.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AliSms {

    @Value("${app.sign_name:}")
    private String signName;

    @Value("${app.template_code:}")
    private String templateCode;

    @Value("${app.aliyun_access_key_id:}")
    private String accessKeyId;

    @Value("${app.aliyun_access_secret:}")
    private String accessSecret;

    public void send(String phone, String name, String time, String work, String option) {
        System.err.println(signName);
        System.err.println(templateCode);
        System.err.println(accessKeyId);
        System.err.println(accessSecret);
        JSONObject data = new JSONObject();
        data.fluentPut("name", name)
            .fluentPut("time", time)
            .fluentPut("work", "工作")
            .fluentPut("option", option);

        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "default");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", data.toJSONString());
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
