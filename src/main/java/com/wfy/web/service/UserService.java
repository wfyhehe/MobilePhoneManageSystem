package com.wfy.web.service;

import com.wfy.web.dao.UserDao;
import com.wfy.web.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/7/14.
 */
@Service
@Transactional
public class UserService {
    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    @Resource
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean exists(User user) {
        return userDao.exists(user);
    }

    public boolean match(User user) {
        return userDao.match(user);
    }
}
