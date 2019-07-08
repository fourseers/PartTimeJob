package com.fourseers.parttimejob.auth.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
public class MerchantUser {

    private Integer uid;
    private String username;
    private String password;
    private Company company;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @NotBlank
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotBlank
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}