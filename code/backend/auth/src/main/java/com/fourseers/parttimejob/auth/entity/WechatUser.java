package com.fourseers.parttimejob.auth.entity;

import javax.persistence.*;

@Entity
@Table(name = "WECHATUSER")
public class WechatUser {

    private Integer uid;
    private String openid;
    private String username;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UID")
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Column(name = "OPENID")
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
