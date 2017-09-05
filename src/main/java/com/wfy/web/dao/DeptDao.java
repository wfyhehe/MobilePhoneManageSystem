package com.wfy.web.dao;

import com.wfy.web.model.Dept;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */

@Repository
public class DeptDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    public List<Dept> getAll() {
        String hql = "from Dept d where d.deleted <> 1 order by d.id";
        List<Dept> depts = (List<Dept>) hibernateTemplate.find(hql);
        return depts;
    }

    public List<Dept> getDeleted() {
        String hql = "from Dept d where d.deleted = 1 order by d.id";
        List<Dept> depts = (List<Dept>) hibernateTemplate.find(hql);
        return depts;
    }

    public Dept getDeptByName(String name) {
        String hql = "from Dept d where d.name = ? and d.deleted <> 1";
        List<Dept> list = (List<Dept>) hibernateTemplate.find(hql, name);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public Dept getDeptById(String id) {
        String hql = "from Dept d where d.id = ? and d.deleted <> 1";
        List<Dept> list = (List<Dept>) hibernateTemplate.find(hql, id);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public void update(Dept dept) {
        hibernateTemplate.update(dept);
    }


    public String save(Dept dept) {
        return (String) hibernateTemplate.save(dept);
    }

    public boolean recover(String id) {
        String hql = "from Dept d where d.id = ? and d.deleted = 1";
        List<Dept> list = (List<Dept>) hibernateTemplate.find(hql, id);
        if (list.size() <= 0) {
            return false;
        }
        Dept dept = list.get(0);
        dept.setDeleted(false);
        return true;
    }
}
