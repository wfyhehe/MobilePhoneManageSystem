package com.wfy.web.dao;

import com.wfy.web.model.Info;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/7/16.
 */
@Repository
public class InfoDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    private Info extractAndNormalizeFirstInfo(List<Info> list) {
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public List<Info> getInfos(String position) {
        String hql = "from Info i where i.position = ? order by i.id";
        List<Info> infos = (List<Info>) hibernateTemplate.find(hql, position);
        return infos;
    }

    public String save(Info info) throws Exception {
        return (String) hibernateTemplate.save(info);
    }

    public Info getInfoById(String id) {
        String hql = "from Info i where i.id = ?";
        List<Info> list = (List<Info>) hibernateTemplate.find(hql, id);
        return extractAndNormalizeFirstInfo(list);
    }

    public void update(Info info) {
        hibernateTemplate.update(info);
    }

    public void delete(Info info) {
        hibernateTemplate.delete(info);
    }

    public void merge(Info info) {
        hibernateTemplate.merge(info);
    }
}
