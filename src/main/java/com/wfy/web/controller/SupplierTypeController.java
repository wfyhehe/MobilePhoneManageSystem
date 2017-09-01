package com.wfy.web.controller;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.SupplierType;
import com.wfy.web.service.ISupplierTypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/26.
 */
@CrossOrigin //TODO 开发使用，发布时必须取消
@RestController
@RequestMapping("/supplier_type/")
public class SupplierTypeController {

    @Resource
    private ISupplierTypeService iSupplierTypeService;

    @RequestMapping(value = "get_supplier_types.do", method = RequestMethod.POST)
    public ServerResponse<List<SupplierType>> getSupplierTypes(@RequestBody Map<String, Object> map) {
        List<SupplierType> supplierTypes = iSupplierTypeService.getSupplierTypes();
        if (supplierTypes != null) {
            return ServerResponse.createBySuccess(supplierTypes);
        } else {
            return ServerResponse.createByErrorMessage("获取供应商类型失败");
        }
    }

    @RequestMapping(value = "add_supplier_type.do", method = RequestMethod.POST)
    public ServerResponse<SupplierType> addMenu(@RequestBody Map<String, Object> supplierTypeMap) {
        SupplierType supplierType = new SupplierType();
        String id = (String) supplierTypeMap.get("id");
        String name = (String) supplierTypeMap.get("name");
        String remark = (String) supplierTypeMap.get("remark");
        supplierType.setId(id);
        supplierType.setName(name);
        supplierType.setRemark(remark);
        try {
            supplierType = iSupplierTypeService.addSupplierType(supplierType);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
        return ServerResponse.createBySuccess(supplierType);
    }

    @RequestMapping(value = "get_deleted_supplier_types.do", method = RequestMethod.GET)
    public ServerResponse<List<SupplierType>> getDeletedSupplierTypes() {
        List<SupplierType> supplierTypes = iSupplierTypeService.getDeletedSupplierTypes();
        if (supplierTypes != null) {
            return ServerResponse.createBySuccess(supplierTypes);
        } else {
            return ServerResponse.createByErrorMessage("获取供应商类型失败");
        }
    }

    @RequestMapping(value = "recover_supplier_type.do", method = RequestMethod.GET)
    public ServerResponse<String> recoverSupplierType(String id) {
        if (iSupplierTypeService.recover(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("恢复失败");
        }
    }

    @RequestMapping(value = "get_supplier_type.do", method = RequestMethod.GET)
    public ServerResponse<SupplierType> getSupplierType(String id) {
        SupplierType supplierType = iSupplierTypeService.getSupplierTypeById(id);
        if (supplierType != null) {
            return ServerResponse.createBySuccess(supplierType);
        } else {
            return ServerResponse.createByErrorMessage("获取供应商类型失败");
        }
    }

    @RequestMapping(value = "update_supplier_type.do", method = RequestMethod.POST)
    public ServerResponse<String> updateSupplierType(@RequestBody Map<String, Object> supplierTypeMap) {
        SupplierType supplierType = new SupplierType();
        String id = (String) supplierTypeMap.get("id");
        String name = (String) supplierTypeMap.get("name");
        String remark = (String) supplierTypeMap.get("remark");
        supplierType.setId(id);
        supplierType.setName(name);
        supplierType.setRemark(remark);
        try {
            supplierType.setDeleted(false);
            iSupplierTypeService.updateSupplierType(supplierType);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("更新失败");
        }
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "delete_supplier_type.do", method = RequestMethod.GET)
    public ServerResponse<String> delete(String id) {
        if (iSupplierTypeService.delete(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("删除失败");
        }
    }

    @RequestMapping(value = "check_supplier_type.do", method = RequestMethod.GET)
    public ServerResponse<String> checkId(String id) {
        if (iSupplierTypeService.idExists(id)) {
            return ServerResponse.createByErrorMessage("编号已存在");
        } else {
            return ServerResponse.createBySuccess();
        }
    }
}
