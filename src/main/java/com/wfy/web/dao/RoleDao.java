package com.wfy.web.dao;

import com.wfy.web.model.Role;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/7/16.
 */

@Repository
public class RoleDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    public Set<Role> getAll() {
        HashSet<Role> set = new HashSet<>();
        String hql = "from Role r";
        set.addAll((List<Role>) hibernateTemplate.find(hql));
        return set;
    }

    public Role getRoleByName(String name) {
        String hql = "from Role r where r.name = ?";
        List<Role> list = (List<Role>) hibernateTemplate.find(hql, name);
        System.out.println(list);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
