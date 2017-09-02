package com.wfy.web.dao;

import com.wfy.web.model.Color;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */

@Repository
public class ColorDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    public List<Color> getAll() {
        String hql = "from Color c order by c.name";
        List<Color> colors = (List<Color>) hibernateTemplate.find(hql);
        return colors;
    }

    public Color getColor(String name) {
        String hql = "from Color c where c.name = ?";
        List<Color> colors = (List<Color>) hibernateTemplate.find(hql, name);
        if (colors.size() > 0) {
            return colors.get(0);
        } else {
            return null;
        }
    }

    public boolean exists(String name) {
        String hql = "select count(*) from Color c where c.name = ?";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql, name);
        return list.get(0) > 0;
    }

    public void save(Color color) {
        hibernateTemplate.save(color);
    }

    public void delete(Color color) {
        hibernateTemplate.delete(color);
    }

    public boolean hasMobiles(Color color) {
        color = getColor(color.getName());
        return color.getMobileModels().size() > 0;
    }
}
