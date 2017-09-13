package com.wfy.web.service;

import com.wfy.web.exceptions.UsernameNotExistsException;
import com.wfy.web.exceptions.WrongPasswordException;
import com.wfy.web.exceptions.WrongVCodeException;
import com.wfy.web.model.Token;
import com.wfy.web.model.User;
import com.wfy.web.model.VCode;

/**
 * Created by Administrator on 2017/9/12, good luck.
 */
public interface IAuthService {
    Token signIn(String username, String password, VCode vCode) throws UsernameNotExistsException, WrongPasswordException, WrongVCodeException;

    void signOut(String userId);

    void signUp(User user) throws Exception;

    void resetPassword(String passwordOld, String passwordNew, String id) throws WrongPasswordException;
}
