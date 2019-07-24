package com.fourseers.parttimejob.warehouse.dto;

import com.fourseers.parttimejob.common.entity.Etc;
import com.fourseers.parttimejob.common.entity.Tag;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class WechatUserInfoDto {

    @NotNull
    private Integer userId;

    private String name;
    private Boolean gender;
    private String identity;
    private String phone;
    private String country;
    private String city;
    private String education;
    private Set<Tag> tags;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(Etc.Education education) { this.education = education.getName(); }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
