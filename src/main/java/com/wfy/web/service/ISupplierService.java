package com.wfy.web.service;

import com.wfy.web.model.Supplier;
import com.wfy.web.utils.RefCount;

import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */
public interface ISupplierService {
    @SuppressWarnings("Duplicates")
    List<Supplier> getSuppliers(RefCount refCount, String name, String type, int pageIndex, int
            pageSize);

    List<Supplier> getDeletedSuppliers();

    Supplier addSupplier(Supplier supplier);

    boolean recover(String id);

    long countSupplier();

    void updateSupplier(Supplier supplier);

    Supplier getSupplierById(String id);

    Supplier getSupplierByName(String name);

    boolean delete(String id);

    boolean idExists(String id);
}
