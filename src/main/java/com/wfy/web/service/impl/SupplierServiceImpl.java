package com.wfy.web.service.impl;

import com.mysql.cj.core.util.StringUtils;
import com.wfy.web.dao.SupplierDao;
import com.wfy.web.dao.SupplierTypeDao;
import com.wfy.web.model.Supplier;
import com.wfy.web.service.ISupplierService;
import com.wfy.web.utils.RefCount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */
@Service("iSupplierService")
@Transactional
public class SupplierServiceImpl implements ISupplierService {

    @Resource
    private SupplierDao supplierDao;

    @Resource
    private SupplierTypeDao typeDao;

    @SuppressWarnings("Duplicates")
    @Override
    public List<Supplier> getSuppliers(RefCount refCount, String name, String type, int pageIndex, int
            pageSize) {
        int offset = (pageIndex - 1) * pageSize;
        if (!StringUtils.isEmptyOrWhitespaceOnly(type)) {
            return supplierDao.search(refCount, name.trim(), type.trim(), offset, pageSize);
        } else {
            if (!StringUtils.isEmptyOrWhitespaceOnly(name)) {
                return supplierDao.search(refCount, name.trim(), offset, pageSize);
            } else {
                return supplierDao.getAll(refCount, offset, pageSize);
            }
        }
    }

    @Override
    public List<Supplier> getDeletedSuppliers() {
        return supplierDao.getDeleted();
    }

    @Override
    public Supplier addSupplier(Supplier supplier) {
        String id = supplierDao.save(supplier);
        supplier.setId(id);
        return supplier;
    }

    @Override
    public boolean recover(String id) {
        return supplierDao.recover(id);
    }

    @Override
    public long countSupplier() {
        return supplierDao.count();
    }

    @Override
    public void updateSupplier(Supplier supplier) {
        supplierDao.update(supplier);
    }

    @Override
    public Supplier getSupplierById(String id) {
        return supplierDao.getSupplierById(id);
    }

    @Override
    public Supplier getSupplierByName(String name) {
        return supplierDao.getSupplierByName(name);
    }

    @Override
    public boolean delete(String id) {
        Supplier supplier = supplierDao.getSupplierById(id);
        if (supplier == null) {
            return false;
        }
        supplier.setDeleted(true);
        supplierDao.update(supplier);
        return true;
    }

    @Override
    public boolean idExists(String id) {
        return supplierDao.idExists(id);
    }


}
