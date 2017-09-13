package com.wfy.web.exceptions;

import com.wfy.web.model.User;

/**
 * Created by Administrator on 2017/9/12, good luck.
 */
public class UsernameExistsException extends RuntimeException {
    public UsernameExistsException() {
        super();
    }

    public UsernameExistsException(String message) {
        super(message);
    }
}
