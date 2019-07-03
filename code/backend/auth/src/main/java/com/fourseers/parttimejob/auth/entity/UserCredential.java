package com.fourseers.parttimejob.auth.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("UserCredential")
public class UserCredential {

    public enum Role {
        ROLE_ADMIN, ROLE_MERCHANT, ROLE_USER
    }

    @Field("username")
    private String username;

    @Field("rid")
    private long rid;

    @Field("password")
    private String password;

    @Field("role")
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