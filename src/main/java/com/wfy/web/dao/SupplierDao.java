package com.wfy.web.dao;

import com.wfy.web.model.Employee;
import com.wfy.web.model.MobileInbound;
import com.wfy.web.model.Supplier;
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

/**
 * Created by Administrator on 2017/8/26.
 */

@Repository
public class SupplierDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    private List<Supplier> normalizeSuppliers(List<Supplier> suppliers) {
        for (Supplier supplier : suppliers) {
            normalizeSupplier(supplier);
        }
        return suppliers;
    }

    private Supplier normalizeSupplier(Supplier supplier) {
        return supplier;
    }

    private Supplier extractAndNormalizeFirstSupplier(List<Supplier> list) {
        if (list.size() > 0) {
            Supplier supplier = list.get(0);
            return normalizeSupplier(supplier);
        } else {
            return null;
        }
    }

    public List<Supplier> search(RefCount refCount, String name, String type, Integer pageIndex, Integer pageSize) {
        List<Supplier> suppliers;
        DetachedCriteria criteria = DetachedCriteria.forClass(Supplier.class, "s")
                .setFetchMode("type", FetchMode.SELECT)
                .add(Restrictions.ne("s.deleted", true))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if (StringUtils.isNotBlank(name)) {
            criteria.add(Restrictions.like("s.name", "%" + name + "%"));
        }
        if (StringUtils.isNotBlank(type)) {
            criteria.createAlias("type", "t")
                    .add(Restrictions.eq("t.name", type));
        }
        DetachedCriteria countCriteria = CloneUtil.clone(criteria);
        countCriteria.setProjection(Projections.rowCount());
        long count = ((List<Long>) hibernateTemplate.findByCriteria(countCriteria)).get(0);
        refCount.setCount(count);
        if (pageIndex != null && pageSize != null) {
            int offset = (pageIndex - 1) * pageSize;
            suppliers = (List<Supplier>) hibernateTemplate.findByCriteria(criteria, offset, pageSize);
        } else {
            suppliers = (List<Supplier>) hibernateTemplate.findByCriteria(criteria);
        }
        return normalizeSuppliers(suppliers);
    }

    public List<Supplier> getDeleted() {
        String hql = "from Supplier s where s.deleted = 1 order by s.id";
        List<Supplier> suppliers = (List<Supplier>) hibernateTemplate.find(hql);
        return normalizeSuppliers(suppliers);
    }

    public Supplier getSupplierByName(String name) {
        String hql = "from Supplier s where s.name = ? and s.deleted <> 1";
        List<Supplier> list = (List<Supplier>) hibernateTemplate.find(hql, name);
        return extractAndNormalizeFirstSupplier(list);
    }

    public Supplier getSupplierById(String id) {
        String hql = "from Supplier s where s.id = ? and s.deleted <> 1";
        List<Supplier> list = (List<Supplier>) hibernateTemplate.find(hql, id);
        return extractAndNormalizeFirstSupplier(list);
    }

    public void update(Supplier supplier) {
        hibernateTemplate.update(supplier);
    }


    public String save(Supplier supplier) {
        return (String) hibernateTemplate.save(supplier);
    }

    public boolean recover(String id) {
        String hql = "from Supplier s where s.id = ? and s.deleted = 1";
        List<Supplier> list = (List<Supplier>) hibernateTemplate.find(hql, id);
        if (list.size() <= 0) {
            return false;
        }
        Supplier supplier = list.get(0);
        supplier.setDeleted(false);
        return true;
    }

    public long count() {
        String hql = "select count(*) from Supplier s where s.deleted <> 1";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql);
        return list.get(0);
    }

    public boolean idExists(String id) {
        String hql = "select count(*) from Supplier s where s.id = ?";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql, id);
        return list.get(0) > 0;
    }


}
