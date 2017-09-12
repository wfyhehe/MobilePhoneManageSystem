package com.wfy.web.model;

import com.wfy.web.common.Const;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2017/8/14.
 */
public class Token {
    private String userId;
    private String credentials;

    public Token() {
    }

    public Token(String userId, String credentials) {
        this.userId = userId;
        this.credentials = credentials;
    }

    public static Token parse(String tokenStr) {
        if (StringUtils.isBlank(tokenStr)) {
            return null;
        }
        String userId = tokenStr.split(Const.TOKEN_SEPERATOR)[0];
        String credentials = tokenStr.split(Const.TOKEN_SEPERATOR)[1];
        return new Token(userId, credentials);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    @Override
    public String toString() {
        return this.userId + Const.TOKEN_SEPERATOR + this.credentials;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (!userId.equals(token.userId)) return false;
        return credentials.equals(token.credentials);
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + credentials.hashCode();
        return result;
    }
}
