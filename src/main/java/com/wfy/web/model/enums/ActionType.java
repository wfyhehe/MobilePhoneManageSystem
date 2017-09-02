package com.wfy.web.model.enums;

/**
 * Created by Administrator on 2017/7/16.
 */
public enum ActionType {
    NORMAL(0, "普通动作"),
    GRANTED(1, "授权动作");

    private final int index;
    private final String value;

    ActionType(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }

}
