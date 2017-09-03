package com.wfy.web.model;

/**
 * Created by Administrator on 2017/8/14.
 */
public class TokenModel {
    private String userId;
    private String token;

    public TokenModel(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public TokenModel() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenModel{" +
                "userId='" + userId + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

}
