package com.fourseers.parttimejob.warehouse.controller;

import com.fourseers.parttimejob.common.entity.Etc;
import com.fourseers.parttimejob.common.entity.Tag;
import com.fourseers.parttimejob.common.util.Response;
import com.fourseers.parttimejob.common.util.ResponseBuilder;
import com.fourseers.parttimejob.warehouse.service.TagService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user")
public class InfoController {

    public static final List<Etc.Education> educationList = Arrays.asList(
            Etc.Education.BELOW_SENIOR,
            Etc.Education.TECHNICAL_JUNIOR,
            Etc.Education.SENIOR_HIGH,
            Etc.Education.JUNIOR_COLLEGE,
            Etc.Education.BACHELOR,
            Etc.Education.ABOVE_BACHELOR);

    private static final int PAGE_SIZE = 10;

    private class Info {
        private List<String> education;
        private List<Tag> tags;

        public List<String> getEducation() {
            return education;
        }

        public void setEducationByEnum(List<Etc.Education> educations) {
            List<String> educationStringList = new ArrayList<>();
            for(Etc.Education edu: educations)
                educationStringList.add(edu.getName());
            this.setEducation(educationStringList);
        }


        public void setEducation(List<String> education) {
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

    @ApiOperation(value = "Get register info, including education and tag")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success")
    })
    @GetMapping(value = "/register-info", produces = "application/json")
    public ResponseEntity<Response<Info>> getUserRegisterInfo() {
        Info info = new Info();
        info.setEducationByEnum(educationList);
        info.setTags(tagService.findAll());
        return ResponseBuilder.build(HttpStatus.OK, info, "success");
    }
}
