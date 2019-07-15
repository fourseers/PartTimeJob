package com.fourseers.parttimejob.common.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags", cascade = CascadeType.REFRESH)
    @JsonBackReference
    private Set<WechatUser> users;

    @ManyToMany(mappedBy = "tagList")
    @JsonIgnore
    private List<Job> jobList;

    // TODO elasticsearch tag vector for similarity


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<WechatUser> getUsers() {
        return users;
    }

    public void setUsers(Set<WechatUser> users) {
        this.users = users;
    }

    public List<Job> getJobList() {
        return jobList;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }
}
