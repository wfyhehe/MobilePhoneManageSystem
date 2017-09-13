package com.wfy.web.exceptions;

/**
 * Created by Administrator on 2017/9/12, good luck.
 */
public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException() {
        super();
    }

    public WrongPasswordException(String message) {
        super(message);
    }
}
