package com.fourseers.parttimejob.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.auth.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/info")
public class InfoController {

    private static String[] education_list = {
            "初中以下", "初中毕业", "高中毕业", "本科毕业", "研究生及以上"
    };

    @Autowired
    private TagService tagService;

    @GetMapping("/user-register")
    public ResponseEntity<JSONObject> getUserRegisterInfo(
            @RequestParam(required = false) Integer pageOffset,
            @RequestParam(required = false) Integer pageSize
    ) {
        JSONObject resp = new JSONObject()
                .fluentPut("education", education_list)
                .fluentPut("tags", tagService.get(pageOffset, pageSize));
        return new ResponseEntity<>(resp, OK);
    }
}