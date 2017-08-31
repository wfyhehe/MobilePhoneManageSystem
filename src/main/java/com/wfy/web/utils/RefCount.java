package com.wfy.web.utils;

/**
 * Created by Administrator on 2017/9/1.
 */
public class RefCount {
    long count;

    public RefCount(long count) {
        this.count = count;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
