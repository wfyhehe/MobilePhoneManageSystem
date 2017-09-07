package com.wfy.web.dao;

import com.wfy.web.model.MobileStock;
import com.wfy.web.utils.CloneUtil;
import com.wfy.web.utils.RefCount;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
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
public class MobileStockDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    private List<MobileStock> normalizeMobileStocks(List<MobileStock> mobileStocks) {
        for (MobileStock mobileStock : mobileStocks) {
            normalizeMobileStock(mobileStock);
        }
        return mobileStocks;
    }

    private MobileStock normalizeMobileStock(MobileStock mobileStock) {
        return mobileStock;
    }

    private MobileStock extractAndNormalizeFirstMobileStock(List<MobileStock> list) {
        if (list.size() > 0) {
            MobileStock mobileStock = list.get(0);
            return normalizeMobileStock(mobileStock);
        } else {
            return null;
        }
    }

    public List<MobileStock> search(RefCount refCount, Date startTime, Date endTime,
                                      String supplier, String model, Integer pageIndex, Integer pageSize) {
        List<MobileStock> mobileStocks;
        DetachedCriteria criteria = DetachedCriteria.forClass(MobileStock.class, "ms")
                .setFetchMode("supplier", FetchMode.SELECT)
                .setFetchMode("mobileModel", FetchMode.SELECT)
                .setFetchMode("user", FetchMode.SELECT)
                .setFetchMode("dept", FetchMode.SELECT)
                .setFetchMode("color", FetchMode.SELECT)
                .setFetchMode("config", FetchMode.SELECT)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if(startTime!=null && endTime != null) {
            criteria.add(Restrictions.between("ms.inputTime",startTime,endTime));
        }
        if (StringUtils.isNotBlank(supplier)) {
            criteria.createAlias("supplier","s")
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
            mobileStocks = (List<MobileStock>) hibernateTemplate.findByCriteria(criteria, offset, pageSize);
        } else {
            mobileStocks = (List<MobileStock>) hibernateTemplate.findByCriteria(criteria);
        }
        return normalizeMobileStocks(mobileStocks);
    }

    public MobileStock getMobileStockById(String id) {
        String hql = "from MobileStock ms where ms.id = ? and ms.deleted <> 1";
        List<MobileStock> list = (List<MobileStock>) hibernateTemplate.find(hql, id);
        return extractAndNormalizeFirstMobileStock(list);
    }

    public void update(MobileStock mobileStock) {
        hibernateTemplate.update(mobileStock);
    }


    public String save(MobileStock mobileStock) {
        return (String) hibernateTemplate.save(mobileStock);
    }

    public boolean recover(String id) {
        String hql = "from MobileStock ms where ms.id = ? and ms.deleted = 1";
        List<MobileStock> list = (List<MobileStock>) hibernateTemplate.find(hql, id);
        if (list.size() <= 0) {
            return false;
        }
        MobileStock mobileStock = list.get(0);
        return true;
    }

    public long count() {
        String hql = "select count(*) from MobileStock ms where ms.deleted <> 1";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql);
        return list.get(0);
    }

    public boolean idExists(String id) {
        String hql = "select count(*) from MobileStock ms where ms.id = ?";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql, id);
        return list.get(0) > 0;
    }


}
