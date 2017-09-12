package com.wfy.web.service;

import com.wfy.web.model.Token;

/**
 * Created by Administrator on 2017/8/16.
 */
public interface ITokenService {
    Token createToken(String userId);

    boolean checkToken(Token token);

    void deleteToken(Token token);
}
