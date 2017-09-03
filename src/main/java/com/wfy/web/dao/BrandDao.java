package com.wfy.web.dao;

import com.wfy.web.model.Brand;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */

@Repository
public class BrandDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    public List<Brand> getAll() {
        String hql = "from Brand c order by c.name";
        List<Brand> brands = (List<Brand>) hibernateTemplate.find(hql);
        return brands;
    }

    public Brand getBrand(String name) {
        String hql = "from Brand c where c.name = ?";
        List<Brand> brands = (List<Brand>) hibernateTemplate.find(hql, name);
        if (brands.size() > 0) {
            return brands.get(0);
        } else {
            return null;
        }
    }

    public boolean exists(String name) {
        String hql = "select count(*) from Brand c where c.name = ?";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql, name);
        return list.get(0) > 0;
    }

    public void save(Brand brand) {
        hibernateTemplate.save(brand);
    }

    public void delete(Brand brand) {
        hibernateTemplate.delete(brand);
    }

    public boolean isInUse(Brand brand) {
        // MobileModel 在使用 Brand
        String hql1 = "select count(mm.brand) from MobileModel mm where mm.brand = ?";
        long count1 = ((List<Long>) hibernateTemplate.find(hql1, brand)).get(0);
        return count1 > 0;
    }
}
