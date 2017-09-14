package com.wfy.web.dao;

import com.wfy.web.model.User;
import com.wfy.web.model.enums.UserStatus;
import com.wfy.web.utils.CloneUtil;
import com.wfy.web.utils.RefCount;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigInteger;
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
        if (user != null
                && user.getEmployee() != null
                && user.getEmployee().getUser() != null
                && user.getEmployee().getUser().equals(user)) {
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

    public User getUserByUsernameAndPassword(String username, String password) {
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

    public List<User> search(RefCount refCount, String username, String empName, Integer pageIndex, Integer pageSize) {
        List<User> users;
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class, "u")
                .setFetchMode("employee", FetchMode.SELECT)
                .add(Restrictions.ne("u.status", UserStatus.DELETED))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if (StringUtils.isNotBlank(username)) {
            criteria.add(Restrictions.like("u.username", "%" + username + "%"));
        }
        if (StringUtils.isNotBlank(empName)) {
            criteria.createAlias("employee", "e")
                    .add(Restrictions.like("e.name", "%" + empName + "%"));
        }
        DetachedCriteria countCriteria = CloneUtil.clone(criteria);
        countCriteria.setProjection(Projections.rowCount());
        long count = ((List<Long>) hibernateTemplate.findByCriteria(countCriteria)).get(0);
        refCount.setCount(count);
        if (pageIndex != null && pageSize != null) {
            int offset = (pageIndex - 1) * pageSize;
            users = (List<User>) hibernateTemplate.findByCriteria(criteria, offset, pageSize);
        } else {
            users = (List<User>) hibernateTemplate.findByCriteria(criteria);
        }
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

    public void merge(User user) {
        hibernateTemplate.merge(user);
    }

    public long count() {
        String hql = "select count(*) from User u where u.status <> 2";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql);
        return list.get(0);
    }

    public boolean isSuperAdmin(String id) {
        String sql = "select count(*) " +
                "from t_user inner join t_role_user inner join t_role" +
                " on t_user.id = t_role_user.user_id " +
                "and t_role.id = t_role_user.role_id " +
                "where t_user.id = :id and t_role.name = :name";
        Object count = hibernateTemplate.executeWithNativeSession(session -> {
            NativeQuery query = session.createNativeQuery(sql);
            query.setParameter("id", id);
            query.setParameter("name", "超级管理员");
            return query.uniqueResult();
        });
        System.out.println((BigInteger) count);
        return ((BigInteger) count).longValue() > 0;
    }
}
