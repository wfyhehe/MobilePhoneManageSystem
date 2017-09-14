package com.wfy.web.service;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.User;
import com.wfy.web.utils.RefCount;

import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */
public interface IUserService {
//    ServerResponse<String> login(String username, String password);

    List<User> getUsers(RefCount refCount, String username, String name
            , Integer pageIndex, Integer pageSize);

    boolean usernameExists(String username);

    User getUser(String id);

    User getUserByName(String name);

    List<User> getDeletedUsers();

    boolean recover(String id);

    void updateUser(User user);

    boolean delete(String id);

    long countUser();

    boolean isSuperAdmin(String id);
}
