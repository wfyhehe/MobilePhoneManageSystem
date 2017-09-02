package com.wfy.web.dao;

import com.wfy.web.model.Role;
import com.wfy.web.model.enums.RoleStatus;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/7/16.
 */

@Repository
public class RoleDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    private List<Role> normalizeRoles(List<Role> roles) {
        for (Role role : roles) {
            normalizeRole(role);
        }
        return roles;
    }

    private Role normalizeRole(Role role) {
        return role;
    }

    private Role extractAndNormalizeFirstRole(List<Role> list) {
        if (list.size() > 0) {
            Role role = list.get(0);
            return normalizeRole(role);
        } else {
            return null;
        }
    }

    public List<Role> getAll() {
        String hql = "from Role r where r.status <> 2 order by r.id";
        List<Role> roles = (List<Role>) hibernateTemplate.find(hql);
        return normalizeRoles(roles);
    }

    public List<Role> getDeleted() {
        String hql = "from Role r where r.status = 2 order by r.id";
        List<Role> roles = (List<Role>) hibernateTemplate.find(hql);
        return normalizeRoles(roles);
    }

    public Role getRoleByName(String name) {
        String hql = "from Role r where r.name = ? and r.status <> 2";
        List<Role> list = (List<Role>) hibernateTemplate.find(hql, name);
        return extractAndNormalizeFirstRole(list);
    }

    public Role getRoleById(String id) {
        String hql = "from Role r where r.id = ? and r.status <> 2";
        List<Role> list = (List<Role>) hibernateTemplate.find(hql, id);
        return extractAndNormalizeFirstRole(list);
    }

    public void update(Role role) {
        hibernateTemplate.update(role);
    }


    public String save(Role role) {
        return (String) hibernateTemplate.save(role);
    }

    public boolean recover(String id) {
        String hql = "from Role r where r.id = ? and r.status = 2";
        List<Role> list = (List<Role>) hibernateTemplate.find(hql, id);
        if (list.size() <= 0) {
            return false;
        }
        Role role = list.get(0);
        role.setStatus(RoleStatus.ONLINE);
        return true;
    }
}
