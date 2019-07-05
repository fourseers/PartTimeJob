package com.fourseers.parttimejob.auth.security;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

@JsonSerialize
public class UserCredential implements Serializable {

    public enum Role {
        ROLE_ADMIN, ROLE_MERCHANT, ROLE_USER
    }

    private String username;

    private long rid;

    private String password;

    private Role role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}