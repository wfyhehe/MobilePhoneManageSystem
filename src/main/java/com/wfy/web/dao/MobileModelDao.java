package com.wfy.web.dao;

import com.wfy.web.model.Employee;
import com.wfy.web.model.MobileInbound;
import com.wfy.web.model.MobileModel;
import com.wfy.web.model.RebatePrice;
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
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/8/26.
 */

@Repository
public class MobileModelDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    private List<MobileModel> normalizeMobileModels(List<MobileModel> mobileModels) {
        for (MobileModel mobileModel : mobileModels) {
            normalizeMobileModel(mobileModel);
        }
        return mobileModels;
    }

    private MobileModel normalizeMobileModel(MobileModel mobileModel) {
        Set<RebatePrice> rebatePrices = mobileModel.getRebatePrices();
        hibernateTemplate.evict(mobileModel);
        for (RebatePrice rebatePrice : rebatePrices) {
            if (rebatePrice.getMobileModel().equals(mobileModel)) {
                rebatePrice.setMobileModel(null);
            }
        }
        hibernateTemplate.clear();
        return mobileModel;
    }

    private MobileModel extractAndNormalizeFirstMobileModel(List<MobileModel> list) {
        if (list.size() > 0) {
            MobileModel mobileModel = list.get(0);
            return normalizeMobileModel(mobileModel);
        } else {
            return null;
        }
    }

    public List<MobileModel> search(RefCount refCount, String name, String brand,
                                    Integer pageIndex, Integer pageSize) {
        List<MobileModel> mobileModels;
        DetachedCriteria criteria = DetachedCriteria.forClass(MobileModel.class, "mm")
                .setFetchMode("brand", FetchMode.SELECT)
                .setFetchMode("rebatePrices", FetchMode.SELECT)
                .add(Restrictions.ne("mm.deleted", true))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if (StringUtils.isNotBlank(name)) {
            criteria.add(Restrictions.like("mm.name", "%" + name + "%"));
        }
        if (StringUtils.isNotBlank(brand)) {
            criteria.createAlias("brand", "b")
                    .add(Restrictions.eq("b.name", brand));
        }
        DetachedCriteria countCriteria = CloneUtil.clone(criteria);
        countCriteria.setProjection(Projections.rowCount());
        long count = ((List<Long>) hibernateTemplate.findByCriteria(countCriteria)).get(0);
        refCount.setCount(count);
        if (pageIndex != null && pageSize != null) {
            int offset = (pageIndex - 1) * pageSize;
            mobileModels = (List<MobileModel>) hibernateTemplate.findByCriteria(criteria, offset, pageSize);
        } else {
            mobileModels = (List<MobileModel>) hibernateTemplate.findByCriteria(criteria);
        }
        return normalizeMobileModels(mobileModels);
    }

    public List<MobileModel> getDeleted() {
        String hql = "from MobileModel mm where mm.deleted = 1 order by mm.id";
        List<MobileModel> mobileModels = (List<MobileModel>) hibernateTemplate.find(hql);
        return normalizeMobileModels(mobileModels);
    }

    public MobileModel getMobileModelByName(String name) {
        String hql = "from MobileModel mm where mm.name = ? and mm.deleted <> 1";
        List<MobileModel> list = (List<MobileModel>) hibernateTemplate.find(hql, name);
        return extractAndNormalizeFirstMobileModel(list);
    }

    public MobileModel getMobileModelById(String id) {
        String hql = "from MobileModel mm where mm.id = ? and mm.deleted <> 1";
        List<MobileModel> list = (List<MobileModel>) hibernateTemplate.find(hql, id);
        return extractAndNormalizeFirstMobileModel(list);
    }

    public void update(MobileModel mobileModel) {
        hibernateTemplate.update(mobileModel);
    }


    public String save(MobileModel mobileModel) {
        return (String) hibernateTemplate.save(mobileModel);
    }

    public boolean recover(String id) {
        String hql = "from MobileModel mm where mm.id = ? and mm.deleted = 1";
        List<MobileModel> list = (List<MobileModel>) hibernateTemplate.find(hql, id);
        if (list.size() <= 0) {
            return false;
        }
        MobileModel mobileModel = list.get(0);
        mobileModel.setDeleted(false);
        return true;
    }

    public long count() {
        String hql = "select count(*) from MobileModel mm where mm.deleted <> 1";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql);
        return list.get(0);
    }

    public boolean idExists(String id) {
        String hql = "select count(*) from MobileModel mm where mm.id = ?";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql, id);
        return list.get(0) > 0;
    }


}
