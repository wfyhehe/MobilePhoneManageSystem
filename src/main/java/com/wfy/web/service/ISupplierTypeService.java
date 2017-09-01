package com.wfy.web.service;

import com.wfy.web.model.SupplierType;

import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */
public interface ISupplierTypeService {
    List<SupplierType> getSupplierTypes();

    List<SupplierType> getDeletedSupplierTypes();

    SupplierType addSupplierType(SupplierType supplierType);

    boolean recover(String id);

    void updateSupplierType(SupplierType supplierType);

    SupplierType getSupplierTypeById(String id);

    SupplierType getSupplierTypeByName(String name);

    boolean delete(String id);

    boolean idExists(String id);
}
