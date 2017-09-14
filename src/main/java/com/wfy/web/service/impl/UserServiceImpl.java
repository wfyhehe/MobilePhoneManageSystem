package com.wfy.web.service.impl;

import com.wfy.web.common.Const;
import com.wfy.web.common.ServerResponse;
import com.wfy.web.dao.UserDao;
import com.wfy.web.model.User;
import com.wfy.web.model.enums.UserStatus;
import com.wfy.web.service.ITokenService;
import com.wfy.web.service.IUserService;
import com.wfy.web.utils.MD5Util;
import com.wfy.web.utils.RefCount;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/14.
 */
@Service("iUserService")
@Transactional
public class UserServiceImpl implements IUserService {

    @Resource
    private UserDao userDao;

    @Override
    public boolean usernameExists(String username) {
        return userDao.exists(username);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public List<User> getUsers(RefCount refCount, String username, String empName
            , Integer pageIndex, Integer pageSize) {
        return userDao.search(refCount, username, empName, pageIndex, pageSize);
    }

    @Override
    public User getUser(String id) {
        return userDao.getUser(id);
    }

    @Override
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    @Override
    public List<User> getDeletedUsers() {
        return userDao.getDeleted();
    }

    @Override
    public boolean recover(String id) {
        return userDao.recover(id);
    }

    @Override
    public void updateUser(User user) {
        User oldUser = userDao.getUser(user.getId());
        if (user.getUsername() == null) {
            user.setUsername(oldUser.getUsername());
        }
        if (user.getEmployee() == null) {
            user.setEmployee(oldUser.getEmployee());
        }
        if (user.getRoles() == null) {
            user.setRoles(oldUser.getRoles());
        }
        if (user.getCreateTime() == null) {
            user.setCreateTime(oldUser.getCreateTime());
        }
        if (user.getStatus() == null) {
            user.setStatus(oldUser.getStatus());
        }
        if (user.getLastLoginTime() == null) {
            user.setLastLoginTime(oldUser.getLastLoginTime());
        }
        if (user.getRemark() == null) {
            user.setRemark(oldUser.getRemark());
        }
        if (user.getRoles() == null) {
            user.setRoles(oldUser.getRoles());
        }
        if (user.getPassword() == null) {
            user.setPassword(oldUser.getPassword());
        }
        userDao.merge(user);
    }

    @Override
    public boolean delete(String id) {
        User user = userDao.getUser(id);
        if (user == null) {
            return false;
        }
        user.setStatus(UserStatus.DELETED);
        userDao.update(user);
        return true;
    }

    @Override
    public long countUser() {
        return userDao.count();
    }

    @Override
    public boolean isSuperAdmin(String id) {
        return userDao.isSuperAdmin(id);
    }
}
