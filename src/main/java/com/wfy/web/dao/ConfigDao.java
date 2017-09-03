package com.wfy.web.dao;

import com.wfy.web.model.Config;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */

@Repository
public class ConfigDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    public List<Config> getAll() {
        String hql = "from Config c order by c.name";
        List<Config> configs = (List<Config>) hibernateTemplate.find(hql);
        return configs;
    }

    public Config getConfig(String name) {
        String hql = "from Config c where c.name = ?";
        List<Config> configs = (List<Config>) hibernateTemplate.find(hql, name);
        if (configs.size() > 0) {
            return configs.get(0);
        } else {
            return null;
        }
    }

    public boolean exists(String name) {
        String hql = "select count(*) from Config c where c.name = ?";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql, name);
        return list.get(0) > 0;
    }

    public void save(Config config) {
        hibernateTemplate.save(config);
    }

    public void delete(Config config) {
        hibernateTemplate.delete(config);
    }

    public boolean isInUse(Config config) {
        // MobileStock, MobileInbound中会使用到Config
        String hql1 = "select count(ms.config) from MobileStock ms where ms.config = ?";
        String hql2 = "select count(mi.config) from MobileInbound mi where mi.config = ?";
        long count1 = ((List<Long>) hibernateTemplate.find(hql1, config)).get(0);
        long count2 = ((List<Long>) hibernateTemplate.find(hql2, config)).get(0);
        return (count1 + count2) > 0;
    }
}
