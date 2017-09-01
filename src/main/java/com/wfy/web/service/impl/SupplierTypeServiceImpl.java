package com.wfy.web.service.impl;

import com.wfy.web.dao.SupplierTypeDao;
import com.wfy.web.model.SupplierType;
import com.wfy.web.service.ISupplierTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */
@Service("iSupplierTypeService")
@Transactional
public class SupplierTypeServiceImpl implements ISupplierTypeService {

    @Resource
    private SupplierTypeDao supplierTypeDao;

    @Override
    public List<SupplierType> getSupplierTypes() {
        return supplierTypeDao.getAll();
    }

    @Override
    public List<SupplierType> getDeletedSupplierTypes() {
        return supplierTypeDao.getDeleted();
    }

    @Override
    public SupplierType addSupplierType(SupplierType supplierType) {
        String id = supplierTypeDao.save(supplierType);
        supplierType.setId(id);
        return supplierType;
    }

    @Override
    public boolean recover(String id) {
        return supplierTypeDao.recover(id);
    }

    @Override
    public void updateSupplierType(SupplierType supplierType) {
        supplierTypeDao.update(supplierType);
    }

    @Override
    public SupplierType getSupplierTypeById(String id) {
        return supplierTypeDao.getSupplierTypeById(id);
    }

    @Override
    public SupplierType getSupplierTypeByName(String name) {
        return supplierTypeDao.getSupplierTypeByName(name);
    }

    @Override
    public boolean delete(String id) {
        SupplierType supplierType = supplierTypeDao.getSupplierTypeById(id);
        if (supplierType == null) {
            return false;
        }
        supplierType.setDeleted(true);
        supplierTypeDao.update(supplierType);
        return true;
    }

    @Override
    public boolean idExists(String id) {
        return supplierTypeDao.idExists(id);
    }
}
