package com.wfy.web.dao;

import com.wfy.web.model.User;
import com.wfy.web.model.UserStatus;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.Date;
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
        users.sort(Comparator.comparingInt(o -> o.getId().hashCode()));
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
        String hql = "from User u where u.username = ?";
        return hibernateTemplate.find(hql, username).size() > 0;
    }

    public User selectLogin(String username, String password) {
        String hql = "from User u where u.username = ? and u.password = ? and u.status <> 2";
        List<User> users = (List<User>) hibernateTemplate.find(hql, username, password);
        return extractAndNormalizeFirstUser(users);
    }

    public void insert(User user) throws Exception {
        hibernateTemplate.save(user);
    }

    public boolean updatePassword(String id, String passwordNew) {
        String hql = "update User u set u.password = ? where u.id = ? and u.status <> 2";
        return hibernateTemplate.bulkUpdate(hql, passwordNew, id) > 0;
    }

    public int checkPassword(String password, String id) {
        String hql = "from User u where u.id = ? and password = ? and u.status <> 2";
        return hibernateTemplate.find(hql, id, password).size();
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

    public void updateStatus(User user, UserStatus status) {
        user.setStatus(status);
        hibernateTemplate.update(user);
    }

    public void updateLoginTime(User user, Date loginTime) {
        user.setLastLoginTime(loginTime);
        hibernateTemplate.update(user);
    }

    public List<User> search(String username, String name) {
        username = "%" + username + "%";
        name = "%" + name + "%";
        String hql = "from User u where u.username like ? and u.status <> 2 or u.employee is not null and u.employee.name like ?";
        List<User> users = (List<User>) hibernateTemplate.find(hql, username, name);
        return normalizeUsers(users);
    }

    public List<User> search(String username) {
        username = "%" + username + "%";
        String hql = "from User u where u.username like ? and u.status <> 2 ";
        List<User> users = (List<User>) hibernateTemplate.find(hql, username);
        return normalizeUsers(users);
    }

    public List<User> getAll() {
        String hql = "from User u where u.status <> 2 ";
        List<User> users = (List<User>) hibernateTemplate.find(hql);
        return normalizeUsers(users);
    }

    public List<User> getDeleted() {
        String hql = "from User u where u.status = 2 ";
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
}
