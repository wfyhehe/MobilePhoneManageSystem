package com.wfy.web.controller;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.Supplier;
import com.wfy.web.service.ISupplierService;
import com.wfy.web.service.ISupplierTypeService;
import com.wfy.web.utils.RefCount;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/26.
 */
@RestController
@RequestMapping("/supplier/")
public class SupplierController {

    @Resource
    private ISupplierService iSupplierService;

    @Resource
    private ISupplierTypeService iSupplierTypeService;


    @RequestMapping(value = "get_suppliers.do", method = RequestMethod.POST)
    public ServerResponse<List<Supplier>> getSuppliers(@RequestBody Map<String, Object> map) {
        String name = (String) map.get("name");
        String type = (String) map.get("type");
        Integer pageIndex = (Integer) map.get("pageIndex");
        Integer pageSize = (Integer) map.get("pageSize");
        RefCount refCount = new RefCount(0);
        List<Supplier> suppliers;
        suppliers = iSupplierService.getSuppliers(refCount, name, type, pageIndex, pageSize);

        //noinspection Duplicates
        if (suppliers != null) {
            ServerResponse response = ServerResponse.createBySuccess(suppliers);
            response.setCount(refCount.getCount());
            return response;
        } else {
            return ServerResponse.createByErrorMessage("获取供应商失败");
        }
    }

    @RequestMapping(value = "add_supplier.do", method = RequestMethod.POST)
    public ServerResponse<Supplier> addSupplier(@RequestBody Map<String, Object> supplierMap) {
        Supplier supplier = new Supplier();
        String id = (String) supplierMap.get("id");
        String name = (String) supplierMap.get("name");
        String typeStr = (String) supplierMap.get("type");
        String contact = (String) supplierMap.get("contact");
        String tel = (String) supplierMap.get("tel");
        String email = (String) supplierMap.get("email");
        String address = (String) supplierMap.get("address");
        String remark = (String) supplierMap.get("remark");
        supplier.setId(id);
        supplier.setName(name);
        supplier.setType(iSupplierTypeService.getSupplierTypeByName(typeStr));
        supplier.setContact(contact);
        supplier.setTel(tel);
        supplier.setEmail(email);
        supplier.setAddress(address);
        supplier.setRemark(remark);
        supplier.setDeleted(false);
        try {
            supplier = iSupplierService.addSupplier(supplier);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
        return ServerResponse.createBySuccess(supplier);
    }

    @RequestMapping(value = "get_deleted_suppliers.do", method = RequestMethod.GET)
    public ServerResponse<List<Supplier>> getDeletedSuppliers() {
        List<Supplier> suppliers = iSupplierService.getDeletedSuppliers();
        if (suppliers != null) {
            return ServerResponse.createBySuccess(suppliers);
        } else {
            return ServerResponse.createByErrorMessage("获取供应商失败");
        }
    }

    @RequestMapping(value = "recover_supplier.do", method = RequestMethod.GET)
    public ServerResponse<String> recoverSupplier(String id) {
        if (iSupplierService.recover(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("恢复失败");
        }
    }

    @RequestMapping(value = "get_supplier.do", method = RequestMethod.GET)
    public ServerResponse<Supplier> getSupplier(String id) {
        Supplier supplier = iSupplierService.getSupplierById(id);
        if (supplier != null) {
            return ServerResponse.createBySuccess(supplier);
        } else {
            return ServerResponse.createByErrorMessage("获取供应商失败");
        }
    }

    @RequestMapping(value = "update_supplier.do", method = RequestMethod.POST)
    public ServerResponse<String> updateSupplier(@RequestBody Map<String, Object> supplierMap) {
        Supplier supplier = new Supplier();
        String id = (String) supplierMap.get("id");
        String name = (String) supplierMap.get("name");
        String typeStr = (String) supplierMap.get("type");
        String contact = (String) supplierMap.get("contact");
        String tel = (String) supplierMap.get("tel");
        String email = (String) supplierMap.get("email");
        String address = (String) supplierMap.get("address");
        String remark = (String) supplierMap.get("remark");
        supplier.setId(id);
        supplier.setName(name);
        supplier.setType(iSupplierTypeService.getSupplierTypeByName(typeStr));
        supplier.setContact(contact);
        supplier.setTel(tel);
        supplier.setEmail(email);
        supplier.setAddress(address);
        supplier.setRemark(remark);
        try {
            supplier.setDeleted(false);
            iSupplierService.updateSupplier(supplier);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("更新失败");
        }
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "delete_supplier.do", method = RequestMethod.GET)
    public ServerResponse<String> delete(String id) {
        if (iSupplierService.delete(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("删除失败");
        }
    }

    @RequestMapping(value = "check_supplier.do", method = RequestMethod.GET)
    public ServerResponse<String> checkId(String id) {
        if (iSupplierService.idExists(id)) {
            return ServerResponse.createByErrorMessage("编号已存在");
        } else {
            return ServerResponse.createBySuccess();
        }
    }

}
