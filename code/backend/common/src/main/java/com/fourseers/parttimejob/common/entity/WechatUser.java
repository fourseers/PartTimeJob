package com.fourseers.parttimejob.common.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class WechatUser {

    private Integer userId;
    private String openid;
    private String name;
    private Boolean gender;
    private String identity;
    private String phone;
    private String country;
    private String city;
    private Etc.Education education;

    private Set<Tag> tags;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
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

    @NotNull
    @Convert(converter = Etc.EducationColumnConverter.class)
    public Etc.Education getEducation() {
        return education;
    }

    public void setEducation(Etc.Education education) {
        this.education = education;
    }

    public void setEducation(String education) {
        this.education = Etc.Education.valueOf(education);
    }

    @ManyToMany(cascade = CascadeType.REFRESH)
    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
