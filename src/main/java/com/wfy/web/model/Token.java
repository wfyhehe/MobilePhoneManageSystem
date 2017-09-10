package com.wfy.web.model;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * Created by Administrator on 2017/8/14.
 */
public class Token {
    public static String parseUserId(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return token.split("#")[0];
    }

    public static String parseUuid(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return token.split("#")[1];
    }

    public static String getToken(String userId, String uuid) {
        return userId + "#" + uuid;
    }

    public static String getToken(String userId) {
        return userId + "#" + UUID.randomUUID().toString();
    }
}
