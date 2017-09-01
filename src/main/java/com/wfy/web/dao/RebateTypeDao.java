package com.wfy.web.dao;

import com.wfy.web.model.RebateType;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */

@Repository
public class RebateTypeDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    private List<RebateType> normalizeRebateTypes(List<RebateType> rebateTypes) {
        for (RebateType rebateType : rebateTypes) {
            normalizeRebateType(rebateType);
        }
        return rebateTypes;
    }

    private RebateType normalizeRebateType(RebateType rebateType) {
        return rebateType;
    }

    private RebateType extractAndNormalizeFirstRebateType(List<RebateType> list) {
        if (list.size() > 0) {
            RebateType rebateType = list.get(0);
            return normalizeRebateType(rebateType);
        } else {
            return null;
        }
    }

    public List<RebateType> getAll() {
        String hql = "from RebateType rt where rt.deleted <> 1 order by rt.id";
        List<RebateType> rebateTypes = (List<RebateType>) hibernateTemplate.find(hql);
        return normalizeRebateTypes(rebateTypes);
    }


    public List<RebateType> getDeleted() {
        String hql = "from RebateType rt where rt.deleted = 1 order by rt.id";
        List<RebateType> rebateTypes = (List<RebateType>) hibernateTemplate.find(hql);
        return normalizeRebateTypes(rebateTypes);
    }

    public RebateType getRebateTypeByName(String name) {
        String hql = "from RebateType rt where rt.name = ? and rt.deleted <> 1";
        List<RebateType> list = (List<RebateType>) hibernateTemplate.find(hql, name);
        return extractAndNormalizeFirstRebateType(list);
    }

    public RebateType getRebateTypeById(String id) {
        String hql = "from RebateType rt where rt.id = ? and rt.deleted <> 1";
        List<RebateType> list = (List<RebateType>) hibernateTemplate.find(hql, id);
        return extractAndNormalizeFirstRebateType(list);
    }

    public void update(RebateType rebateType) {
        hibernateTemplate.update(rebateType);
    }

    public String save(RebateType rebateType) {
        return (String) hibernateTemplate.save(rebateType);
    }

    public boolean recover(String id) {
        String hql = "from RebateType rt where rt.id = ? and rt.deleted = 1";
        List<RebateType> list = (List<RebateType>) hibernateTemplate.find(hql, id);
        if (list.size() <= 0) {
            return false;
        }
        RebateType rebateType = list.get(0);
        rebateType.setDeleted(false);
        return true;
    }

    public boolean idExists(String id) {
        String hql = "select count(*) from RebateType rt where rt.id = ?";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql, id);
        return list.get(0) > 0;
    }
}
