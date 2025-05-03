package com.example.sayaproject.model;

import java.util.Calendar;
import java.util.Date;

public class Token {
    Long id;
    Long userId;
    String token;
    Date expireTime;

    public Token() {

    }

    public Token(Long id, Long userId, String token, Date expireTime) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.expireTime = expireTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public boolean isValid() {
        return (new Date().getTime() <= expireTime.getTime());
    }
}
