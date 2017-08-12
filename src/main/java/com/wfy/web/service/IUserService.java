package com.wfy.web.service;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.User;

/**
 * Created by Administrator on 2017/8/8.
 */
public interface IUserService {
    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str, String type);

    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);

    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);
}
