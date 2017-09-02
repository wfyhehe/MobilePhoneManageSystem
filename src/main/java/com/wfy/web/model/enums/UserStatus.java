package com.wfy.web.model.enums;

/**
 * Created by Administrator on 2017/7/16.
 */
public enum UserStatus {
    ONLINE(0, "ONLINE"),
    OFFLINE(1, "OFFLINE"),
    DELETED(2, "DELETED");

    private final int index;
    private final String value;

    UserStatus(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public static UserStatus get(String str) {
        for (UserStatus status : values()) {
            if (status.getValue().equals(str)) {
                return status;
            }
        }
        return null;
    }

    public int getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }
}
