package com.wfy.web.exceptions;

/**
 * Created by Administrator on 2017/9/12, good luck.
 */
public class WrongVCodeException extends RuntimeException {
    public WrongVCodeException() {
        super();
    }

    public WrongVCodeException(String message) {
        super(message);
    }
}
