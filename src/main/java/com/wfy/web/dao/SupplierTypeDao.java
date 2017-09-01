package com.wfy.web.dao;

import com.wfy.web.model.SupplierType;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */

@Repository
public class SupplierTypeDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    private List<SupplierType> normalizeSupplierTypes(List<SupplierType> supplierTypes) {
        for (SupplierType supplierType : supplierTypes) {
            normalizeSupplierType(supplierType);
        }
        return supplierTypes;
    }

    private SupplierType normalizeSupplierType(SupplierType supplierType) {
        return supplierType;
    }

    private SupplierType extractAndNormalizeFirstSupplierType(List<SupplierType> list) {
        if (list.size() > 0) {
            SupplierType supplierType = list.get(0);
            return normalizeSupplierType(supplierType);
        } else {
            return null;
        }
    }

    public List<SupplierType> getAll() {
        String hql = "from SupplierType st where st.deleted <> 1 order by st.id";
        List<SupplierType> supplierTypes = (List<SupplierType>) hibernateTemplate.find(hql);
        return normalizeSupplierTypes(supplierTypes);
    }


    public List<SupplierType> getDeleted() {
        String hql = "from SupplierType st where st.deleted = 1 order by st.id";
        List<SupplierType> supplierTypes = (List<SupplierType>) hibernateTemplate.find(hql);
        return normalizeSupplierTypes(supplierTypes);
    }

    public SupplierType getSupplierTypeByName(String name) {
        String hql = "from SupplierType st where st.name = ? and st.deleted <> 1";
        List<SupplierType> list = (List<SupplierType>) hibernateTemplate.find(hql, name);
        return extractAndNormalizeFirstSupplierType(list);
    }

    public SupplierType getSupplierTypeById(String id) {
        String hql = "from SupplierType st where st.id = ? and st.deleted <> 1";
        List<SupplierType> list = (List<SupplierType>) hibernateTemplate.find(hql, id);
        return extractAndNormalizeFirstSupplierType(list);
    }

    public void update(SupplierType supplierType) {
        hibernateTemplate.update(supplierType);
    }

    public String save(SupplierType supplierType) {
        return (String) hibernateTemplate.save(supplierType);
    }

    public boolean recover(String id) {
        String hql = "from SupplierType st where st.id = ? and st.deleted = 1";
        List<SupplierType> list = (List<SupplierType>) hibernateTemplate.find(hql, id);
        if (list.size() <= 0) {
            return false;
        }
        SupplierType supplierType = list.get(0);
        supplierType.setDeleted(false);
        return true;
    }

    public boolean idExists(String id) {
        String hql = "select count(*) from SupplierType st where st.id = ?";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql, id);
        return list.get(0) > 0;
    }
}
