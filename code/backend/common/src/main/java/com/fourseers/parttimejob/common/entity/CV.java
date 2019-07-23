package com.fourseers.parttimejob.common.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Convert;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Document("CV")
public class CV {

    @Id
    private String id;

    @Convert(converter = Etc.EducationColumnConverter.class)
    private Etc.Education education;
    private String content;

    @NotNull
    private Integer userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Etc.Education getEducation() {
        return education;
    }

    public void setEducation(Etc.Education education) {
        this.education = education;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
