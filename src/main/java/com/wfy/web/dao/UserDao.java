package com.wfy.web.dao;

import com.wfy.web.model.User;
import com.wfy.web.model.UserStatus;
import com.wfy.web.utils.PaginationUtil;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/7/14.
 */
@Repository
public class UserDao {

    private HibernateTemplate hibernateTemplate;

    public UserDao() {
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    @Resource
    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    private List<User> normalizeUsers(List<User> users) {
        for (User user : users) {
            normalizeUser(user);
        }
        return users;
    }

    private User normalizeUser(User user) {
        if (user.getEmployee() != null && user.getEmployee().getUser().equals(user)) {
            hibernateTemplate.evict(user);
            user.getEmployee().setUser(null); // 从属的employee的user属性设为空,防止无限递归
            hibernateTemplate.clear();
        }
        return user;
    }

    private User extractAndNormalizeFirstUser(List<User> list) {
        if (list.size() > 0) {
            User user = list.get(0);
            return normalizeUser(user);
        } else {
            return null;
        }
    }

    public boolean exists(User user) {
        return exists(user.getUsername());
    }

    public boolean exists(String username) {
        String hql = "select count(*) from User u where u.username = ?";
        return ((List<Long>) hibernateTemplate.find(hql, username)).get(0) > 0;
    }

    public User selectLogin(String username, String password) {
        String hql = "from User u where u.username = ? and u.password = ? and u.status <> 2";
        List<User> users = (List<User>) hibernateTemplate.find(hql, username, password);
        return extractAndNormalizeFirstUser(users);
    }

    public void insert(User user) throws Exception {
        hibernateTemplate.save(user);
    }

    public boolean checkPassword(String password, String id) {
        String hql = "select count(*) from User u where u.id = ? and password = ? and u.status <>" +
                " 2";
        return ((List<Long>) hibernateTemplate.find(hql, id, password)).get(0) > 0;
    }

    public User getUser(String id) {
        String hql = "from User u where u.id = ? and u.status <> 2";
        List<User> users = (List<User>) hibernateTemplate.find(hql, id);
        return extractAndNormalizeFirstUser(users);
    }

    public User getUserByName(String name) {
        String hql = "from User u where u.username = ? and u.status <> 2";
        List<User> users = (List<User>) hibernateTemplate.find(hql, name);
        return extractAndNormalizeFirstUser(users);
    }

    public List<User> search(String username, String name, int offset, int length) {
        username = "%" + username + "%";
        name = "%" + name + "%";
        String hql = "from User u where u.username like ? and u.status <> 2 or u.employee is not " +
                "null and u.employee.name like ? order by u.id";
        List<User> users = PaginationUtil.pagination(hibernateTemplate, offset, length, hql, name);
        return normalizeUsers(users);
    }

    public List<User> search(String username, int offset, int length) {
        username = "%" + username + "%";
        String hql = "from User u where u.username like ? and u.status <> 2 order by u.id";
        List<User> users = PaginationUtil.pagination(hibernateTemplate, offset, length, hql,
                username);
        return normalizeUsers(users);
    }

    public List<User> getAll(int offset, int length) {
        String hql = "from User u where u.status <> 2 order by u.id";
        List<User> users = PaginationUtil.pagination(hibernateTemplate, offset, length, hql);
        return normalizeUsers(users);
    }

    public List<User> getDeleted() {
        String hql = "from User u where u.status = 2 order by u.id";
        List<User> users = (List<User>) hibernateTemplate.find(hql);
        return normalizeUsers(users);
    }

    public boolean recover(String id) {
        String hql = "from User u where u.id = ? and u.status = 2";
        List<User> list = (List<User>) hibernateTemplate.find(hql, id);
        if (list.size() <= 0) {
            return false;
        }
        User user = list.get(0);
        user.setStatus(UserStatus.ONLINE);
        return true;
    }

    public void update(User user) {
        hibernateTemplate.update(user);
    }

    public long count() {
        String hql = "select count(*) from User u where u.status <> 2";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql);
        return list.get(0);
    }
}
