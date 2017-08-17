package com.wfy.web.service;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.TokenModel;
import com.wfy.web.model.User;

/**
 * Created by Administrator on 2017/8/8.
 */
public interface IUserService {
    ServerResponse<TokenModel> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkUsername(String username);

    ServerResponse<String> checkValid(String str, String type);

//    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);

    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, String id);

    User getUser(String id);

    void logout(String userId);
}
