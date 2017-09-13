package com.wfy.web.exceptions;

/**
 * Created by Administrator on 2017/9/12, good luck.
 */
public class UsernameNotExistsException extends RuntimeException {
    public UsernameNotExistsException() {
        super();
    }

    public UsernameNotExistsException(String message) {
        super(message);
    }
}
