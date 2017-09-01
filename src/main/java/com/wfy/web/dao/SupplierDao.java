package com.wfy.web.dao;

import com.wfy.web.model.Supplier;
import com.wfy.web.utils.PaginationUtil;
import com.wfy.web.utils.RefCount;
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

    public List<Supplier> getAll(RefCount refCount, int offset, int length) {
        String hql = "from Supplier s where s.deleted <> 1 order by s.id";
        refCount.setCount(((List<Long>) hibernateTemplate.find("select count(*) " + hql)).get(0));
        List<Supplier> suppliers = PaginationUtil.pagination(hibernateTemplate,
                offset, length, hql);
        return normalizeSuppliers(suppliers);
    }

    public List<Supplier> search(RefCount refCount, String name, String type, int offset, int length) {
        name = "%" + name + "%";
        type = "%" + type + "%";
        String hql = "from Supplier s where s.name like ? and s.type.id in (" +
                "select t.id from SupplierType t where t.name like ? and t.deleted <> 1" +
                ") and s.deleted <> 1 order by s.id";
        List<Long> countList = (List<Long>) hibernateTemplate.find("select count(*) " + hql,
                name, type);
        refCount.setCount(countList.get(0));
        List<Supplier> suppliers = PaginationUtil.pagination(
                hibernateTemplate, offset, length, hql, name, type);
        return normalizeSuppliers(suppliers);
    }

    public List<Supplier> search(RefCount refCount, String name, int offset, int length) {
        name = "%" + name + "%";
        String hql = "from Supplier s where s.name like ? and s.deleted <> 1 order by s.id";
        refCount.setCount(((List<Long>) hibernateTemplate.find("select count(*) " + hql, name))
                .get(0));
        List<Supplier> suppliers = PaginationUtil.pagination(hibernateTemplate,
                offset, length, hql, name);
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
