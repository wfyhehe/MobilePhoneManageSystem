package com.wfy.web.service;

import com.wfy.web.model.TokenModel;

/**
 * Created by Administrator on 2017/8/16.
 */
public interface ITokenService {
    TokenModel createToken(String userId);

    boolean checkToken(TokenModel model);

    void deleteToken(String userId);
}
