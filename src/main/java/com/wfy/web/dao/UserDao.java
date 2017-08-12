package com.wfy.web.dao;

import com.wfy.web.model.User;
import com.wfy.web.model.UserStatus;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/7/14.
 */
@Repository
public class UserDao {
    private User user;
    private HibernateTemplate hibernateTemplate;

    public UserDao(User user) {
        this.user = user;
    }

    public UserDao() {
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    @Resource
    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean exists(User user) {
        return exists(user.getUsername());
    }

    public boolean exists(String username) {
        String hql = "from User u where u.username = ?";
        return hibernateTemplate.find(hql, username).size() > 0;
    }

    public User selectLogin(String username, String password) {
        String hql = "from User u where u.username = ? and u.password = ?";
        List<User> users = (List<User>) hibernateTemplate.find(hql, username, password);
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public void insert(User user) throws Exception {
        hibernateTemplate.save(user);
    }

    public int updatePasswordByUsername(String username, String passwordNew) {
        String hql = "update User u set u.password = ? where u.username = ?";
        return hibernateTemplate.bulkUpdate(hql, passwordNew, username);
    }

    public int checkPassword(String password, String id) {
        String hql = "from User u where u.id = ? and password = ?";
        return hibernateTemplate.find(hql, id, password).size();
    }
}
