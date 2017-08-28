package com.wfy.web.dao;

import com.wfy.web.model.Role;
import com.wfy.web.model.RoleStatus;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2017/7/16.
 */

@Repository
public class RoleDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    public List<Role> getAll() {
        String hql = "from Role r where r.status <> 2";
        List<Role> roles = (List<Role>) hibernateTemplate.find(hql);
        roles.sort(Comparator.comparingInt(o -> o.getId().hashCode()));
        return roles;
    }

    public List<Role> getDeleted() {
        String hql = "from Role r where r.status = 2";
        List<Role> roles = (List<Role>) hibernateTemplate.find(hql);
        roles.sort(Comparator.comparingInt(o -> o.getId().hashCode()));
        return roles;
    }

    public Role getRoleByName(String name) {
        String hql = "from Role r where r.name = ? and r.status <> 2";
        List<Role> list = (List<Role>) hibernateTemplate.find(hql, name);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public Role getRoleById(String id) {
        String hql = "from Role r where r.id = ? and r.status <> 2";
        List<Role> list = (List<Role>) hibernateTemplate.find(hql, id);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
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
        if(list.size() <= 0) {
            return false;
        }
        Role role = list.get(0);
        role.setStatus(RoleStatus.ONLINE);
        return true;
    }
}
