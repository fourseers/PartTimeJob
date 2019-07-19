package com.fourseers.parttimejob.warehouse.controller;

import com.fourseers.parttimejob.common.entity.Tag;
import com.fourseers.parttimejob.common.util.Response;
import com.fourseers.parttimejob.common.util.ResponseBuilder;
import com.fourseers.parttimejob.warehouse.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user")
public class InfoController {

    private static String[] education_list = {
            "初中以下", "初中毕业", "高中毕业", "本科毕业", "研究生及以上"
    };

    private static final int PAGE_SIZE = 10;

    private class Info {
        private List<String> education;
        private List<Tag> tags;

        public List<String> getEducation() {
            return education;
        }

        void setEducation(List<String> education) {
            this.education = education;
        }

        public List<Tag> getTags() {
            return tags;
        }

        void setTags(List<Tag> tags) {
            this.tags = tags;
        }
    }

    @Autowired
    private TagService tagService;

    @GetMapping("/register-info")
    public ResponseEntity<Response<Info>> getUserRegisterInfo() {
        Info info = new Info();
        info.setEducation(Arrays.asList(education_list));
        info.setTags(tagService.findAll());
        return ResponseBuilder.build(HttpStatus.OK, info, "success");
    }
}
