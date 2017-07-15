package com.wfy.web.dao;

import com.wfy.web.model.User;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/7/14.
 */
@Component
@Transactional
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
        String username = user.getUsername();
        String hql = "from User u where u.username = ?";
        return hibernateTemplate.find(hql, username).size() > 0;

    }

    public boolean match(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String hql = "from User u where u.username = ? and password = ?";
        return hibernateTemplate.find(hql, username, password).size() > 0;
    }
}
