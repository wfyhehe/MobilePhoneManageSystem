package com.wfy.web.service;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.TokenModel;
import com.wfy.web.model.User;

import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */
public interface IUserService {
    ServerResponse<TokenModel> login(String username, String password);

    List<User> getUsers(String username, String name, int pageIndex, int pageSize);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkUsername(String username);

    ServerResponse<String> checkValid(String str, String type);

//    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);

    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, String id);

    User getUser(String id);

    User getUserByName(String name);

    void logout(String userId);

    List<User> getDeletedUsers();

    boolean recover(String id);

    void updateUser(User user);

    boolean delete(String id);

    long countUser();
}
