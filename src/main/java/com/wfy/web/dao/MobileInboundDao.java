package com.wfy.web.dao;

import com.wfy.web.model.MobileInbound;
import com.wfy.web.utils.CloneUtil;
import com.wfy.web.utils.RefCount;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */

@Repository
public class MobileInboundDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    private List<MobileInbound> normalizeMobileInbounds(List<MobileInbound> mobileInbounds) {
        for (MobileInbound mobileInbound : mobileInbounds) {
            normalizeMobileInbound(mobileInbound);
        }
        return mobileInbounds;
    }

    private MobileInbound normalizeMobileInbound(MobileInbound mobileInbound) {
        return mobileInbound;
    }

    private MobileInbound extractAndNormalizeFirstMobileInbound(List<MobileInbound> list) {
        if (list.size() > 0) {
            MobileInbound mobileInbound = list.get(0);
            return normalizeMobileInbound(mobileInbound);
        } else {
            return null;
        }
    }

    public List<MobileInbound> search(RefCount refCount, Date startTime, Date endTime,
                                      String supplier, String model, Integer pageIndex, Integer pageSize) {
        List<MobileInbound> mobileInbounds;
        DetachedCriteria criteria = DetachedCriteria.forClass(MobileInbound.class, "mi")
                .setFetchMode("supplier", FetchMode.SELECT)
                .setFetchMode("mobileModel", FetchMode.SELECT)
                .setFetchMode("user", FetchMode.SELECT)
                .setFetchMode("dept", FetchMode.SELECT)
                .setFetchMode("color", FetchMode.SELECT)
                .setFetchMode("config", FetchMode.SELECT)
                .setFetchMode("mobiles", FetchMode.SELECT)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if (startTime != null && endTime != null) {
            criteria.add(Restrictions.between("mi.inputTime", startTime, endTime));
        }
        if (StringUtils.isNotBlank(supplier)) {
            criteria.createAlias("supplier", "s")
                    .add(Restrictions.eq("s.name", supplier));
        }
        if (StringUtils.isNotBlank(model)) {
            criteria.createAlias("mobileModel", "mm")
                    .add(Restrictions.eq("mm.name", model));
        }
        DetachedCriteria countCriteria = CloneUtil.clone(criteria);
        countCriteria.setProjection(Projections.rowCount());
        long count = ((List<Long>) hibernateTemplate.findByCriteria(countCriteria)).get(0);
        refCount.setCount(count);
        if (pageIndex != null && pageSize != null) {
            int offset = (pageIndex - 1) * pageSize;
            mobileInbounds = (List<MobileInbound>) hibernateTemplate.findByCriteria(criteria, offset, pageSize);
        } else {
            mobileInbounds = (List<MobileInbound>) hibernateTemplate.findByCriteria(criteria);
        }
//        for (MobileInbound mobileInbound : mobileInbounds) {
//            Hibernate.initialize(mobileInbound.getMobiles());
//        }
        return normalizeMobileInbounds(mobileInbounds);
    }

    public List<MobileInbound> getDeleted() {
        String hql = "from MobileInbound mi where mi.deleted = 1 order by mi.id";
        List<MobileInbound> mobileInbounds = (List<MobileInbound>) hibernateTemplate.find(hql);
        return normalizeMobileInbounds(mobileInbounds);
    }

    public MobileInbound getMobileInboundByName(String name) {
        String hql = "from MobileInbound mi where mi.name = ? and mi.deleted <> 1";
        List<MobileInbound> list = (List<MobileInbound>) hibernateTemplate.find(hql, name);
        return extractAndNormalizeFirstMobileInbound(list);
    }

    public MobileInbound getMobileInboundById(String id) {
        String hql = "from MobileInbound mi where mi.id = ? and mi.deleted <> 1";
        List<MobileInbound> list = (List<MobileInbound>) hibernateTemplate.find(hql, id);
        return extractAndNormalizeFirstMobileInbound(list);
    }

    public void update(MobileInbound mobileInbound) {
        hibernateTemplate.update(mobileInbound);
    }


    public String save(MobileInbound mobileInbound) {
        return (String) hibernateTemplate.save(mobileInbound);
    }

    public boolean recover(String id) {
        String hql = "from MobileInbound mi where mi.id = ? and mi.deleted = 1";
        List<MobileInbound> list = (List<MobileInbound>) hibernateTemplate.find(hql, id);
        if (list.size() <= 0) {
            return false;
        }
        MobileInbound mobileInbound = list.get(0);
        return true;
    }

    public long count() {
        String hql = "select count(*) from MobileInbound mi where mi.deleted <> 1";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql);
        return list.get(0);
    }

    public boolean idExists(String id) {
        String hql = "select count(*) from MobileInbound mi where mi.id = ?";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql, id);
        return list.get(0) > 0;
    }


}
